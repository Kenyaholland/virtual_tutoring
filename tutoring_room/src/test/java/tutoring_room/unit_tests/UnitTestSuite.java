package tutoring_room.unit_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@Suite.SuiteClasses ({
	RegLoginTests.class,
	DirectoryTests.class,
	RoomTests.class
})
public class UnitTestSuite {
	
}
