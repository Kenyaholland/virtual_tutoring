package brogrammers.tutoring_room.controllers;

import java.util.UUID;

import brogrammers.tutoring_room.models.Session;

/*
 * Handles interaction between general view and session model
 */
public class SessionController {

	private String username;
	private Session mySession;
	
	public SessionController(String username)
	{
		this.username = username;
		this.mySession = null;
	}
	
	public void openSession()
	{
		System.out.println("SessionController - opening session");
		String sessionId = UUID.randomUUID().toString();
		
		mySession = new Session(username);
		mySession.open(sessionId, username);
	}
	
	public void closeSession()
	{
		System.out.println("SessionController - closing session");
		mySession.close();
		mySession = null;
	}
	
	public boolean getActiveSession() 
	{
		if (mySession == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Session getSession()
	{
		return mySession;
	}
	
	public String getSessionId()
	{
		return mySession.getSessionId();
	}
}
