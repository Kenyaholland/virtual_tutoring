package tutoring_room.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import brogrammers.tutoring_room.controllers.RoomController;
import brogrammers.tutoring_room.controllers.SessionController;
import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.reglogin.Registration;
import brogrammers.tutoring_room.reglogin.User;

/**
 * The Brogrammers 
 * CIS4592 Capstone Project 
 * Spring 2022
 * 
 * Collection of unit tests for tutor room operations
 */
public class RoomTests {

	@Test
	public void joinAndLeaveGroupTest() {
		// Register user
		Registration reg = new Registration();
		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
				
		// Begin session
		SessionController sessionCtrl = new SessionController("username1");
		sessionCtrl.openSession();
		String sessionId = sessionCtrl.getSessionId();
		
		RoomController roomCtrl = new RoomController(1, sessionId);
		UserDAO dao = new UserDAO();
		dao.connectToDatabase();
		
		// Testing joining breakout group
		int init_group = dao.getGroupNum("username1");
		roomCtrl.joinBreakoutGroup(1);
		int result_group = dao.getGroupNum("username1");
		assertTrue((result_group != init_group) && (result_group == 1));
		
		// Testing leaving breakout group
		init_group = result_group;
		roomCtrl.removeFromBreakoutGroup();
		result_group = dao.getGroupNum("username1");
		assertTrue((result_group != init_group) && (result_group == 0));
		
		sessionCtrl.closeSession();
		dao.deleteUser(temp_user);
	}

}
