package brogrammers.tutoring_room.models;

import brogrammers.tutoring_room.data_access.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private int groupNum;
	private int roomNum;
	private List<String> groupNames;
	private UserDAO dao;
	
	public Group(int groupNum, int roomNum)
	{
		this.groupNum = groupNum;
		this.roomNum = roomNum;
		groupNames = new ArrayList<String>();
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
		groupNames.add(username);
	}
	
	public void removeUser(String username)
	{
		groupNames.remove(username);
	}
	
	public List<String> getNames()
	{
		dao.connectToDatabase();
		this.groupNames = dao.getGroupNames(roomNum, groupNum);
		dao.closeConnection();
		return this.groupNames;
	}
}
