package brogrammers.tutoring_room.controllers;

import brogrammers.tutoring_room.models.Directory;

public class DirectoryController {

	private Directory directory;
	private String sessionId;
	
	public DirectoryController(String sessionId) 
	{
		this.sessionId = sessionId;
		directory = new Directory(this.sessionId);
	}
	
	public void addUserToRoom(int roomNum)
	{
		directory.addActiveUserToRoom(roomNum);
	}
	
	public int getNumStudentsInRoom(int roomNum)
	{
		return directory.getNumStudentsInRoom(roomNum);
	}
	
	public void removeFromRoom()
	{
		directory.removeActiveUserFromRoom();
	}
	
//	public String getTutorNameForRoom(int roomNum)
//	{
//		return directory.getTutorNameForRoom(roomNum);
//	}
}
