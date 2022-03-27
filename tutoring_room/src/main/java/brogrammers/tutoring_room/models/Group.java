package brogrammers.tutoring_room.models;

import brogrammers.tutoring_room.data_access.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private int groupNum;
	private int roomNum;
	private List<String> groupUsernames;
	private UserDAO dao;
	
	public Group(int groupNum, int roomNum)
	{
		this.groupNum = groupNum;
		this.roomNum = roomNum;
		groupUsernames = new ArrayList<String>();
		dao = new UserDAO();
	}
	
	public int getGroupNum()
	{
		return this.groupNum;
	}
	
	public int getRoomNum()
	{
		return this.roomNum;
	}
	
	public void addUser(String username) 
	{
		groupUsernames.add(username);
	}
	
	public void removeUser(String username)
	{
		groupUsernames.remove(username);
	}
	
	public List<String> getGroupUsernames()
	{
		dao.connectToDatabase();
		this.groupUsernames = dao.getGroupUsernames(roomNum, groupNum);
		dao.closeConnection();
		return this.groupUsernames;
	}
}
