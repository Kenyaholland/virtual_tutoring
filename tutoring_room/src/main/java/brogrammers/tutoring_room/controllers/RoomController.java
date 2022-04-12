package brogrammers.tutoring_room.controllers;

import brogrammers.tutoring_room.models.Room;

public class RoomController {
	
	private Room room;
	
	public RoomController(int roomNum, String sessionId)
	{
		room = new Room(roomNum, sessionId);
	}
	
	public void joinBreakoutGroup(int groupNum)
	{
		room.addActiveUserToGroup(groupNum);
	}
	
	public void removeFromBreakoutGroup()
	{
		room.removeActiveUserFromGroup();
	}
	
	public String getGroupMembers(int groupNum)
	{
		final int MAX_CHAR = 50;
		final int WRAP_POINT = 25;
		int linePoint = 0;
		String groupMembersStr = "";
		
		if (!room.getGroupUsernames(groupNum).isEmpty()) {
			for (String username : room.getGroupUsernames(groupNum)) {
				int usernameLen = username.length();
				
				if (groupMembersStr.isEmpty()) {
					groupMembersStr += username;
				}
				else if ((groupMembersStr.length() + usernameLen) <= MAX_CHAR) {
					if ((linePoint + usernameLen) >= WRAP_POINT) {
						groupMembersStr += ",\n" + username;
						linePoint = usernameLen;
					}
					else {
						groupMembersStr += ", " + username;
						linePoint += usernameLen + 2;
					}
				}
				else {
					groupMembersStr += ", ...";
				}
			}
		}
		else {
			groupMembersStr = "-- currently empty --";
		}
		
		return groupMembersStr;
	}

}
