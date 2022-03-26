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
			close();
		}
	}
	
	// send a message
	public Boolean send(String message) {
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			return true;
		} catch (IOException e) {
			close();
		}
		return false;
	}
	
	// get latest message
	public String receive() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			close();
		}
		return null;
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
	
	public static void main(String[] args) {
				
		// get our client's user-name
		Scanner scanner = new Scanner(System.in);
		System.out.print("username: ");
		String username = scanner.nextLine();
		System.out.print("room number: ");
		String roomNumber = scanner.nextLine();
		
		// connect to the server
		Client client = new Client("localhost", 55555);
		
		// send the server our user name and room number (this is protocol for our server)
		client.send(username);
		client.send(roomNumber);
		
		// print incoming messages (threaded so we are not blocked)
		new Thread(new Runnable() {
			@Override
			public void run() {
				String message;
				while (!Thread.interrupted()) {
					message = client.receive();
					if (message != null) {
						System.out.println(message);
					} else {
						break;
					}
				}
			}
		}).start();	
		
		// send console input
		String message;
		while (!(message = scanner.nextLine()).equals("")) {
			if (!client.send(message)) {
				break;
			}
		}
		scanner.close();
	}
}