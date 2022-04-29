# **Virtual Tutoring Room - The Brogrammers**

## **Source Code Content**

- virtual_tutoring/tutoring_room - root project directory, contains source code, external JARs and pom.xml file
- virtual_tutoring/tutoring_room/src - project code directory
    - ./main/java - source code
        - ./brogrammers/tutor_room - default package, contains App class to start the application and client/server related classes
            - ./reg_login - contains classes related to registration and login functionality
            - ./data_access - contains DAO classes for database operations
            - ./views - contains classes related to the user interface / presentation
            - ./controllers - contains classes related to connecting the backend logic to the views
            - ./models - contains classes that represent core objects that hold data and operations
    - ./test/java/tutoring_room - test code
        - ./unit_tests - unit test suite
        - ./integration_test - one simple integration-type test

## **Set-up Instructions**

1. Clone the HTTPS link to a folder that holds your projects.
2. Open Eclipse, set the workspace to the folder that holds your projects.
3. Select "Import Projects" in the package explorer.
4. In the window that opens, select "General" drop down arrow, and click "Existing Projects into Workspace."
5. Click "Browse" and select the "virtual_tutoring" Folder. Then click Finish.
** Note: If Eclipse recognizes two options for how to open the project (virtual_tutoring and virtual_tutoring/tutoring_room), select virtual_tutoring/tutoring_room

## **Things to do before running the app**

(Finish these in detail)
1. Ngrok
2. Database connection setup: For the program to be able to connect to the database, you must access the Amazon Web Services EC2 Management Console and add your IP to the inbound rules.
    
      ** Note: Dr. OK, please refer to an email from tyh2 with the subject "Brogrammers Database Connectivity Instructions (Capstone)" for instructions on how to do this. I have sent this via email to avoid any security problems with my AWS account.
4. Zoom App marketplace


## **Run the app**
Now you are ready to start the application.
1. Run OAuthServer.java
2. Run ChatServer.java
3. Run App.java

Enjoy!
