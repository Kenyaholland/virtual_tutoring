package brogrammers.tutoring_room.controllers;

import brogrammers.tutoring_room.ChatClient;

public class ClientController {
	private ChatClient client;
	
	public ClientController(String address, int port) {
		client = new ChatClient(address, port);
	}
	public void sendUsername(String username) {
		try {
			client.send(username);
		} catch (Exception e) {
			System.out.println("Failed to send username to server");
			client.close();
		}
	}
	public void leaveRoom() {
        try {
        	client.send("0");	
        } catch (Exception e) {
        	System.out.println("Failed to leave server room");
        	client.close();
        }
	}
	public void joinRoom(String room) {
        try {
        	client.send("1" + room);	
        } catch (Exception e) {
        	System.out.println("Failed to join server room");
        	client.close();
        }
	}
	public void sendMessage(String message) {
        try {
        	client.send("2" + message);	
        } catch (Exception e) {
        	System.out.println("Failed to send message to server");
        	client.close();
        }
	}
	public String getMessage() throws Exception {
		return client.receive();
	}
	public void disconnect() {
		client.close();
	}
}
