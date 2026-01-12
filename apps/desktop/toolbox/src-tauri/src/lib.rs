use std::sync::Mutex;

mod tcp_server;
mod tcp_client;

use tcp_server::{ServerState, start_tcp_server, stop_tcp_server};
use tcp_client::{ClientState, start_tcp_client, stop_tcp_client, send_tcp_client_message};

// Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .manage(Mutex::new(ServerState { shutdown_tx: None }))
        .manage(Mutex::new(ClientState { shutdown_tx: None, writer: None }))
        .invoke_handler(tauri::generate_handler![
            greet, 
            start_tcp_server, 
            stop_tcp_server,
            start_tcp_client,
            stop_tcp_client,
            send_tcp_client_message
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
