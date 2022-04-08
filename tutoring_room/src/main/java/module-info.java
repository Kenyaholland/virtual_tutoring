module brogrammers.tutoring_room {
	requires java.naming;
	requires java.mail;
	requires java.sql;
	requires org.apache.commons.lang3;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.base;
	requires org.controlsfx.controls;
	//requires mysql.connector.java;
    exports brogrammers.tutoring_room;
}
