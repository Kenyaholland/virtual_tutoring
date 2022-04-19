package brogrammers.tutoring_room.models;

import brogrammers.tutoring_room.data_access.SessionDAO;
import brogrammers.tutoring_room.data_access.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private int roomNum;
	private String tutorUsername;
	private final String sessionId;
	private final int NUM_GROUPS = 4;
	private List<Group> breakoutGroups;
	private List<String> courses;
	private SessionDAO sdao;
	private UserDAO udao;
	
	public Room(int roomNum, String sessionId)
	{
		this.roomNum = roomNum;
		this.sessionId = sessionId;
		this.sdao = new SessionDAO();
		this.udao = new UserDAO();
			
		breakoutGroups = new ArrayList<Group>();
		for (int i = 0; i < NUM_GROUPS; i++) {
			breakoutGroups.add(new Group(i+1, this.roomNum));
		}
	}
	
	public void setTutor(String username) {
		this.tutorUsername = username;
	}
	
	public String getTutor()
	{
		return this.tutorUsername;
	}
	
	public void addCourse(String crn)
	{
		courses.add(crn);
	}
	
	public void addUser()
	{
		sdao.connectToDatabase();
		udao.connectToDatabase();
		
		String username = sdao.getActiveUserName(sessionId);
		
		if(username != null) {
			udao.setRoomNum(username, roomNum);
		}

		sdao.closeConnection();
		udao.closeConnection();
	}
	
	public int getNumUsers()
	{
		udao.connectToDatabase();
		int numUsers = udao.getNumUsersInRoom(roomNum);
		udao.closeConnection();
		
		return numUsers;
	}
	
	public void removeActiveUser()
	{
		sdao.connectToDatabase();
		udao.connectToDatabase();
		
		String username = sdao.getActiveUserName(sessionId);
		
		if(username != null) {
			udao.setRoomNum(username, 0);
		}
		
		System.out.println("Set room to 0 for user " + username);

		sdao.closeConnection();
		udao.closeConnection();
	}
	
	/*
	 * Gets active user's username and sets their group number in the users table
	 * @param groupNum number of breakout group to join
	 */
	public void addActiveUserToGroup(int groupNum)
	{
		sdao.connectToDatabase();
		udao.connectToDatabase();
		
		String username = sdao.getActiveUserName(sessionId);
		
		if(username != null) {
			udao.setGroupNum(username, groupNum);
		}

		System.out.println("Added group " + groupNum + " for user " + username + " - Click refresh to update page");
		sdao.closeConnection();
		udao.closeConnection();
		breakoutGroups.get(groupNum-1).addUser(username);
	}
	
	/*
	 * Gets active user's username and sets their group number to 0 in the users table
	 */
	public void removeActiveUserFromGroup()
	{
		sdao.connectToDatabase();
		udao.connectToDatabase();
		
		String username = sdao.getActiveUserName(sessionId);
		int groupNum = udao.getGroupNum(username);
		
		if(username != null) {
			udao.setGroupNum(username, 0);
		}

		System.out.println("Set group to 0 for user " + username);
		sdao.closeConnection();
		udao.closeConnection();
		breakoutGroups.get(groupNum).removeUser(username);
	}
	
	static int getActiveUserRoomNum(String _sessionId)
	{
		final SessionDAO _sdao = new SessionDAO();
		final UserDAO _udao = new UserDAO();
		_sdao.connectToDatabase();
		_udao.connectToDatabase();
		
		String username = _sdao.getActiveUserName(_sessionId);
		int roomNum = 0;
		
		if(username != null) {
			roomNum = _udao.getRoomNum(username);
		}

		_sdao.closeConnection();
		_udao.closeConnection();
		
		return roomNum;
	}
	
	public List<String> getGroupUsernames(int groupNum)
	{
		List<String> groupUsernames = breakoutGroups.get(groupNum-1).getGroupUsernames();
		return groupUsernames;
	}

}
