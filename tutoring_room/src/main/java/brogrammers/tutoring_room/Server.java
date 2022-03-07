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
			System.out.println("server ready");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {	
		try {
			System.out.println("server started");
			while (!serverSocket.isClosed()) {
				System.out.println("waiting for connections");
				
				Socket socket = serverSocket.accept();
				System.out.println("incoming connection accepted");
				
				ClientHandler clientHandler = new ClientHandler(socket);		
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void close() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ClientHandler implements Runnable {
		
		private Socket socket;
		private BufferedReader bufferedReader;
		private BufferedWriter bufferedWriter;
		private String username;
		
		public ClientHandler(Socket socket) {
			try {
				this.socket = socket;
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				username = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
		}

		@Override
		public void run() {
			clientHandlers.add(this);
			broadcast(username + " connected");
			
			String message;
			while (socket.isConnected()) {
				try {
					message = bufferedReader.readLine();
					broadcast(message);
				} catch (IOException e) {
					break;
				}
			}
			
			clientHandlers.remove(this);
			broadcast(username + " disconnected");
			close();	
		}
		
		public void broadcast(String message) {
			for (ClientHandler clientHandler : clientHandlers) {
				try {
					clientHandler.bufferedWriter.write(message);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
					close();
				}
			}
		}
		
		public void close() {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(55555);
		server.start();
	}
}