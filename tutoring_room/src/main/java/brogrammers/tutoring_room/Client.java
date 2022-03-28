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
	
	public void send(String message) throws IOException {
		bufferedWriter.write(message);
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}
	
	public String receive() throws IOException {
		return bufferedReader.readLine();
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
		Scanner scanner = new Scanner(System.in);
		System.out.print("Username: ");
		String username = scanner.nextLine();
		
		Client client = new Client("localhost", 55555);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println(client.receive());	
					}
				} catch (IOException e) {
					client.close();
				}
			}
		}).start();	
		
		try {
			client.send(username);
			
			while (true) {
				client.send(scanner.nextLine());
			}
		} catch (IOException e) {
			client.close();
		} finally {
			scanner.close();
		}		
	}
}