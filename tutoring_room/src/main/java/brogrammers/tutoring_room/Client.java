package brogrammers.tutoring_room;

import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}
	
	// send a message
	public void send(String message) {
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();	
		} catch (IOException e) {
			close();
		}
	}
	
	// get latest message
	public String receive() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			close();
		}
		return "";
	}
	
	// get socket (for checking connection)
	public Socket getSocket() {
		return socket;
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
	
	public static void main(String[] args) {
				
		//	get our client's user-name
		System.out.print("username: ");
		Scanner scanner = new Scanner(System.in); 
		String username = scanner.nextLine();
		
		// connect to the server
		Client client = new Client("localhost", 55555);
			
		// send the server our user name (this is protocol for our server)
		client.send(username);
		
		// print incoming messages (threaded so we are not blocked)
		// for app gui, we will need to print incoming messages to a 'textbox'
		new Thread(new Runnable() {
			@Override
			public void run() {
				String message;
				while (client.getSocket().isConnected()) {
					if (!(message = client.receive()).equals("")) {
						System.out.println(message);
					}
				}
			}
		}).start();	
		
		// send console input
		// for app gui, we will need to receive input from a 'textbox'
		String message;
		while (client.getSocket().isConnected()) {
			if (!(message = scanner.nextLine()).equals("")) {
				client.send(message);
			}
		}
		scanner.close();
	}
}