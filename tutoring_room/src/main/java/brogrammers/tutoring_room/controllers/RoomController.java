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
		final int WRAP_POINT = 30;
		int linePoint = 0;
		String groupMembersStr = "";
		
		if (!room.getGroupNames(groupNum).isEmpty()) {
			for (String name : room.getGroupNames(groupNum)) {
				int nameLen = name.length();
				if (groupMembersStr.isEmpty()) {
					groupMembersStr += name;
				}
				else if ((groupMembersStr.length() + nameLen) <= MAX_CHAR) {
					if ((linePoint + nameLen) >= WRAP_POINT) {
						groupMembersStr += ",\n" + name;
						linePoint = nameLen;
					}
					else {
						groupMembersStr += ", " + name;
						linePoint += nameLen + 2;
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
