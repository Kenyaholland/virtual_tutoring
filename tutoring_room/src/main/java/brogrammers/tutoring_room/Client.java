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
	private Scanner scanner;
	private String username;
	
	public Client(String address, int port) {
		scanner = new Scanner(System.in);
		System.out.print("username: ");
		username = scanner.nextLine();

		try {	
			socket = new Socket(address, port);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}
	
	public void send() {
		try {
			String message;
			while (socket.isConnected()) {
				message = scanner.nextLine();
				if (!message.equals("")) {
					bufferedWriter.write(username + ": " + message);
					bufferedWriter.newLine();
					bufferedWriter.flush();
				}
			}
			scanner.close();
		} catch (IOException e) {
			close();
		}
	}
	
	public void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String message;
				while (socket.isConnected()) {
					try {
						message = bufferedReader.readLine();
						System.out.println(message);						
					} catch (IOException e) {
						close();
					}

				}
			}
		}).start();
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
		Client client = new Client("localhost", 55555);
		client.receive();
		client.send();
	}
}