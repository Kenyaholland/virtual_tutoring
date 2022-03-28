package brogrammers.tutoring_room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private ServerSocket serverSocket;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server ready");
			
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("User connected");
				
				ClientHandler clientHandler = new ClientHandler(socket);		
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class ClientHandler implements Runnable {
		
		private Socket socket;
		private BufferedReader bufferedReader;
		private BufferedWriter bufferedWriter;
		private String username;
		private String room;
		
		public ClientHandler(Socket socket) {
			try {
				this.socket = socket;
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
		}

		@Override
		public void run() {
			try {
				username = bufferedReader.readLine();
				
				clientHandlers.add(this);
				
				String message;
				while (true) {
					try {
						message = bufferedReader.readLine();
						if (!message.equals("")) {
							if (message.charAt(0) == '0') {
								broadcast(username + " disconnected");
								room = null;
							} else if (message.charAt(0) == '1') {
								broadcast(username + " disconnected");
								room = String.valueOf(message.substring(1));
								broadcast(username + " connected");
							} else if (message.charAt(0) == '2') {
								broadcast(username + ": " + message.substring(1));
							}
						}
					} catch (IOException e) {
						break;
					}
				}
	
				clientHandlers.remove(this);
				broadcast(username + " disconnected");	
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.out.println("User disconnected");
				close();	
			}
		}
		
		public void broadcast(String message) {
			try {
				if (room != null) {
					for (ClientHandler clientHandler : clientHandlers) {
						if (room.equals(clientHandler.getRoom())) {
							clientHandler.bufferedWriter.write(message);
							clientHandler.bufferedWriter.newLine();
							clientHandler.bufferedWriter.flush();
						}
					}	
				}
			} catch (IOException e) {
				close();
			}	
		}
		
		public String getUsername() {
			return username;
		}

		public String getRoom() {
			return room;
		}
		
		public void close() {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Server(55555);
	}
}