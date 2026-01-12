use tauri::{AppHandle, Emitter, State};
use std::sync::Mutex;
use tokio::net::TcpStream;
use tokio::io::{AsyncReadExt, AsyncWriteExt};
use tokio::sync::broadcast;
use tokio::net::tcp::OwnedWriteHalf;

pub struct ClientState {
    pub shutdown_tx: Option<broadcast::Sender<()>>,
    pub writer: Option<OwnedWriteHalf>,
}

#[tauri::command]
pub async fn start_tcp_client(
    ip: String,
    port: u16,
    state: State<'_, Mutex<ClientState>>,
    app: AppHandle,
) -> Result<(), String> {
    // 1. Check if already running
    {
        let state_guard = state.lock().map_err(|e| e.to_string())?;
        if state_guard.shutdown_tx.is_some() {
            return Err("Client is already connected".into());
        }
    }

    let addr = format!("{}:{}", ip, port);
    // 2. Connect asynchronously
    let stream = TcpStream::connect(&addr).await.map_err(|e| e.to_string())?;
    let (mut reader, writer) = stream.into_split();
    
    // 3. Update state
    let mut state_guard = state.lock().map_err(|e| e.to_string())?;
     if state_guard.shutdown_tx.is_some() {
        return Err("Client is already connected".into());
    }

    let (tx, mut rx) = broadcast::channel(1);
    state_guard.shutdown_tx = Some(tx);
    state_guard.writer = Some(writer);
    
    let app_handle = app.clone();

    let _ = app_handle.emit("client-log", format!("Connected to {}", addr));

    tokio::spawn(async move {
        let mut buf = [0; 1024];
        loop {
            tokio::select! {
                _ = rx.recv() => {
                    let _ = app_handle.emit("client-log", "Disconnecting...");
                    break;
                }
                read_result = reader.read(&mut buf) => {
                     match read_result {
                        Ok(0) => {
                             let _ = app_handle.emit("client-log", "Connection closed by server");
                             let _ = app_handle.emit("client-disconnected", ());
                             break; 
                        }
                        Ok(n) => {
                             let msg = String::from_utf8_lossy(&buf[..n]);
                             let _ = app_handle.emit("client-log", format!("Received: {}", msg));
                        }
                        Err(e) => {
                             let _ = app_handle.emit("client-log", format!("Error reading: {}", e));
                             let _ = app_handle.emit("client-disconnected", ());
                             break;
                        }
                     }
                }
            }
        }
        let _ = app_handle.emit("client-log", "Client disconnected");
    });

    Ok(())
}

#[tauri::command]
pub async fn stop_tcp_client(state: State<'_, Mutex<ClientState>>) -> Result<(), String> {
    let mut state_guard = state.lock().map_err(|e| e.to_string())?;
    
    if let Some(tx) = state_guard.shutdown_tx.take() {
        let _ = tx.send(());
    }
    
    state_guard.writer = None;
    
    Ok(())
}

#[tauri::command]
pub async fn send_tcp_client_message(
    message: String,
    state: State<'_, Mutex<ClientState>>,
) -> Result<(), String> {
    let mut writer = {
        let mut state_guard = state.lock().map_err(|e| e.to_string())?;
        state_guard.writer.take().ok_or("Client is not connected")?
    };

    let result = async {
        writer.write_all(message.as_bytes()).await.map_err(|e| e.to_string())?;
        writer.flush().await.map_err(|e| e.to_string())?;
        Ok(())
    }.await;

    // Put it back
    let mut state_guard = state.lock().map_err(|e| e.to_string())?;
    if state_guard.shutdown_tx.is_some() {
        state_guard.writer = Some(writer);
    } else {
        // Client stopped while we were writing, so just drop the writer (it will close)
    }

    result
}