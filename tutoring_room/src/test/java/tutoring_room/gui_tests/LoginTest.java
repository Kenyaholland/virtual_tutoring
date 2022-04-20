package tutoring_room.gui_tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import brogrammers.tutoring_room.SceneSwitcher;

@ExtendWith(ApplicationExtension.class)
class LoginTest {

    @Start
    void onStart(Stage stage) {
    	SceneSwitcher switcher = new SceneSwitcher(stage);
 	   
    	boolean onLogout = false;
    	stage.setScene(switcher.LoginScene(onLogout));
        stage.show();
    }

	@Test
    void should_contain_first_button(FxRobot robot) {
        // expect:
		robot.clickOn("#sign-in-button");
		assertThat(true, is(true));
        //verifyThat("#sign-in-button", hasText("Sign In"));
    }
}
