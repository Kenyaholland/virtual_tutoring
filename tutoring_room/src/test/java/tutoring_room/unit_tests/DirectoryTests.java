package tutoring_room.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import brogrammers.tutoring_room.controllers.DirectoryController;
import brogrammers.tutoring_room.controllers.SessionController;
import brogrammers.tutoring_room.data_access.UserDAO;
import brogrammers.tutoring_room.reglogin.Registration;
import brogrammers.tutoring_room.reglogin.User;


public class DirectoryTests {

	public DirectoryTests() {}
	
	@Test
	public void enterAndLeaveRoomTest() {
		
		// Register user
		Registration reg = new Registration();
		User temp_user = reg.register("fname1", "lname1", "username1", "Password1!", "email1@uwf.edu");
		
		// Begin session
		SessionController sessionCtrl = new SessionController("username1");
		sessionCtrl.openSession();
		String sessionId = sessionCtrl.getSessionId();
		
		DirectoryController dir = new DirectoryController(sessionId);
		UserDAO dao = new UserDAO();
		
		// Testing entering room
		int init_count = dir.getNumStudentsInRoom(1);
		dir.addUserToRoom(1);
		int result_count = dir.getNumStudentsInRoom(1);
		assertTrue(result_count == (init_count + 1));
		
		// Testing leaving room
		init_count = result_count;
		dir.removeFromRoom();
		result_count = dir.getNumStudentsInRoom(1);
		assertTrue(result_count == (init_count - 1));
		
		sessionCtrl.closeSession();
		dao.connectToDatabase();
		dao.deleteUser(temp_user);
		dao.closeConnection();
	}
	
}
