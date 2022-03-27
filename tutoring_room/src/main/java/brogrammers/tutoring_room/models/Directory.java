package brogrammers.tutoring_room.models;

import java.util.ArrayList;

import brogrammers.tutoring_room.data_access.DirectoryDAO;

public class Directory {

	private ArrayList<Room> rooms;
	private String sessionId;
	private DirectoryDAO ddao;
	
	public Directory (String sessionId) 
	{
		this.sessionId = sessionId;
		rooms = new ArrayList<Room>();
		ddao = new DirectoryDAO();
		
		for (int i = 0; i < 6; i++) {
			rooms.add(new Room(i+1, sessionId));
		}
	}
	
	public void addActiveUserToRoom(int roomNum)
	{
		rooms.get(roomNum-1).addUser();
		ddao.connectToDatabase();
		ddao.incrementNumStudentsInRoom(roomNum);
		ddao.closeConnection();
	}
	
	public int getNumStudentsInRoom(int roomNum)
	{
		ddao.connectToDatabase();
		int numStudents = ddao.getNumStudentsInRoom(roomNum);
		ddao.closeConnection();
		return numStudents;
	}
	
	public void removeActiveUserFromRoom()
	{
		int roomNum = Room.getActiveUserRoomNum(sessionId);
		rooms.get(roomNum-1).removeActiveUser();

		ddao.connectToDatabase();		
		ddao.decrementNumStudentsInRoom(roomNum);
		ddao.closeConnection();
	}
	
}
