package brogrammers.tutoring_room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/*
 * Setup
 * 1. Create an OAuth app on the Zoom app marketplace
 * Creating this app will generate your OAuth client ID and secret
 * The app can only be authorized by developers that have been added to the app unless the app is published to the marketplace****
 * 
 * 2. Fill out OAuth app information to activate the app
 * 
 * 3. Add scopes
 * Adding scopes specifies which permissions the app will have access to
 * 
 * 4. Install Ngrok and setup as required
 * Run Ngrok on port 55556
 * 
 * 5. Copy Ngrok redirect url into variable below and on the OAuth app setup page
 * 
 * 6. Copy app's client ID and secret to the variables below
 * 
 * 7. Run this Oauth server below, this server is set to run on port 55556
 * 
 * 8. Make a GET request to this server to receive zoom's redirect url
 * This can be done by opening your browser to localhost:55556
 * The browser will automatically redirect the received url
 * But this will need to be done by our app
 * This can be implemented using a state query (this is not yet implemented)***
 * 
 * 9. The rest is done automatically
 * After the client authorizes the app, zoom will send a request to this server
 * Zoom's request will contain a code query
 * With the code query, this server will send another request to zoom
 * Asking to exchange the code for an access token
 * Zoom will then send the access token back to this server
 * The access token is used to send GET requests to zoom to access user information
 */

public class OAuthServer {
	
	/* these cannot be stored here
	 * they must be placed in a .gitignore file
	 */
	static final String clientID = "";
	static final String clientSecret = "";
	static final String redirectURL = "";
	
	public OAuthServer(int port) {	
		// start server
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("OAuth server running on port: " + port);
			
			// create a client for sending requests
	        HttpClient httpClient = HttpClient.newBuilder()
	                .version(HttpClient.Version.HTTP_2)
	                .build();
			
			// begin listening
			while (true) {
				// accept incoming socket
				Socket socket = serverSocket.accept();
				
				// create streams
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                
                // parse HTTP request
                String request = bufferedReader.readLine();
                String[] requestArray = request.split(" ");
                System.out.println(Arrays.toString(requestArray));
                
                // get query parameters
                String route = requestArray[1];
                Map<String, String> paramsMap = new HashMap<String, String>();
            	if (route.contains("?")) {
            		String params = route.substring(route.indexOf("?") + 1);
            		String[] paramsArray = params.split("&");
            		for (String param : paramsArray) {
            			String[] split = param.split("=");
            			if (split.length == 2) {
            				paramsMap.put(split[0], split[1]);	
            			}
            		}
            	}
            	
        		if (paramsMap.containsKey("code")) {
        			/* when we receive a URL with a code query
        			 * we must exchange the code for an access token
        			 * by sending a request to zoom with the code
        			 */	
                	try {
                		// encode the authorization code
                        String basic = clientID + ":" + clientSecret;
                        String encoded = Base64.getEncoder().encodeToString(basic.getBytes());
                        String body = "grant_type=authorization_code&code=" + paramsMap.get("code") + "&redirect_uri=" + redirectURL;
                        
                        // build the post request
                        HttpRequest req = HttpRequest.newBuilder()
                                .uri(URI.create("https://zoom.us/oauth/token"))
                                .setHeader("Authorization", "Basic " + encoded)
                                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                                .POST(HttpRequest.BodyPublishers.ofString(body))
                                .build();
                        
                        // send the post request to zoom
                        HttpResponse<String> response = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
                        JSONObject json = new JSONObject(response.body());
                        
                        // send zoom's response back to requesting client
                    	bufferedWriter.write("HTTP/1.1 200 OK" + "\r\n"
                    			+ "Content-Type: application/json" + "\r\n"
                    			+ "\r\n");
                    	bufferedWriter.write(json.toString(4));
                    	bufferedWriter.flush();

                	} catch (InterruptedException e) {
                		e.printStackTrace();
                	}
        		} else {
        			/* redirect the client to zoom with our info
        			 * after the client accepts authorization,
        			 * zoom will send a request to our server with a code query
        			 */
                	bufferedWriter.write("HTTP/1.1 302 Found\r\n"
                			+ "Location: https://zoom.us/oauth/authorize?response_type=code&client_id=" + clientID + "&redirect_uri=" + redirectURL + "\r\n"
                			+ "\r\n");
                	bufferedWriter.flush();
        		}
                socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		new OAuthServer(55556);
	}
}