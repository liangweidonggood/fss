use tauri::{AppHandle, Emitter, State};
use std::sync::Mutex;
use tokio::net::TcpListener;
use tokio::io::AsyncReadExt;
use tokio::sync::broadcast;

pub struct ServerState {
    pub shutdown_tx: Option<broadcast::Sender<()>>,
}

#[tauri::command]
pub async fn start_tcp_server(
    port: u16,
    state: State<'_, Mutex<ServerState>>,
    app: AppHandle,
) -> Result<(), String> {
    // 1. Check if already running (initial check)
    {
        let state_guard = state.lock().map_err(|e| e.to_string())?;
        if state_guard.shutdown_tx.is_some() {
            return Err("Server is already running".into());
        }
    } // Drop lock here

    let addr = format!("0.0.0.0:{}", port);
    // 2. Try to bind asynchronously without holding the lock
    let listener = TcpListener::bind(&addr).await.map_err(|e| e.to_string())?;

    // 3. Acquire lock again to update state
    let mut state_guard = state.lock().map_err(|e| e.to_string())?;
    
    // Double check (race condition handling)
    if state_guard.shutdown_tx.is_some() {
        return Err("Server is already running".into());
    }

    let (tx, mut rx) = broadcast::channel(1);
    state_guard.shutdown_tx = Some(tx.clone());

    let app_handle = app.clone();
    
    let _ = app_handle.emit("server-log", format!("Server listening on {}", addr));

    // Spawn the server task
    tokio::spawn(async move {
        loop {
            tokio::select! {
                _ = rx.recv() => {
                    let _ = app_handle.emit("server-log", "Server stopping...");
                    break;
                }
                accept_result = listener.accept() => {
                    match accept_result {
                        Ok((mut socket, addr)) => {
                            let _ = app_handle.emit("server-log", format!("New connection from: {}", addr));
                            let app_handle_clone = app_handle.clone();
                            let mut rx_conn = tx.subscribe();
                            
                            // Handle connection
                            tokio::spawn(async move {
                                let mut buf = [0; 1024];
                                loop {
                                    tokio::select! {
                                        _ = rx_conn.recv() => {
                                            // Server shutdown signal received, stop reading
                                            break;
                                        }
                                        read_result = socket.read(&mut buf) => {
                                            match read_result {
                                                Ok(0) => {
                                                        let _ = app_handle_clone.emit("server-log", format!("Connection closed: {}", addr));
                                                    break;
                                                }
                                                Ok(n) => {
                                                    let msg = String::from_utf8_lossy(&buf[..n]);
                                                    let _ = app_handle_clone.emit("server-log", format!("[{}]: {}", addr, msg));
                                                }
                                                Err(e) => {
                                                        let _ = app_handle_clone.emit("server-log", format!("Error reading from {}: {}", addr, e));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                        Err(e) => {
                                let _ = app_handle.emit("server-log", format!("Accept error: {}", e));
                        }
                    }
                }
            }
        }
        let _ = app_handle.emit("server-log", "Server stopped");
    });

    Ok(())
}

#[tauri::command]
pub async fn stop_tcp_server(state: State<'_, Mutex<ServerState>>) -> Result<(), String> {
    let mut state_guard = state.lock().map_err(|e| e.to_string())?;
    
    if let Some(tx) = state_guard.shutdown_tx.take() {
        let _ = tx.send(());
    }
    
    Ok(())
}