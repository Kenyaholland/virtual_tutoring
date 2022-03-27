/*
 * A container for application session info
 * Session.java
 */
package brogrammers.tutoring_room.models;

import brogrammers.tutoring_room.data_access.SessionDAO;
import brogrammers.tutoring_room.data_access.UserDAO;

public class Session {

	private UserDAO udao;
	private SessionDAO sdao;
	private String sessionId;
	
	public Session (String username) 
	{
		this.udao = new UserDAO();
		this.sdao = new SessionDAO();
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void open(String sessionId, String username)
	{
		System.out.println("Session - opening");

		this.sessionId = sessionId;
		udao.connectToDatabase();
		sdao.connectToDatabase();
		
		if (udao.checkExistenceOfUserName(username)) {
			sdao.addSession(this.sessionId, username);
		}
		
		udao.closeConnection();
		sdao.closeConnection();
	}
	
	public void close()
	{
		System.out.println("Session - closing");
		sdao.connectToDatabase();
		
		sdao.removeSession(this.sessionId);
		sdao.closeConnection();
	}
}
