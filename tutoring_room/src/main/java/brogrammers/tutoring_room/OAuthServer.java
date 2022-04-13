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
 * 8. Run the Oauth client
 * 		(the client does not check if an access code exists atm, it just assumes)****
 */

public class OAuthServer {
	// these need to be placed in a .gitignore
	static final String clientID = "";
	static final String clientSecret = "";
	static final String redirectURL = "";
	
	private ServerSocket serverSocket;
	private Map<String, String> clientCodes;
	
	public OAuthServer(int port) {	
		try {
			// start server
			serverSocket = new ServerSocket(port);
			clientCodes = new HashMap<String, String>();
			System.out.println("OAuth server ready");
			
			// handle incoming requests
			while (true) {
				Socket socket = serverSocket.accept();
				RequestHandler clientHandler = new RequestHandler(socket);		
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public class RequestHandler implements Runnable {
		
		private Socket socket;
		private BufferedReader bufferedReader;
		private BufferedWriter bufferedWriter;
		
		public RequestHandler(Socket socket) {
			try {
				this.socket = socket;
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
		}

		@Override
		public void run() {
			try {
				// parse request
				String request = bufferedReader.readLine();
				String[] requestArray = request.split(" ");
				System.out.println(Arrays.toString(requestArray));
				
				if (requestArray.length == 3 && requestArray[1].contains("?")) {
					
					// parse query parameters
					Map<String, String> paramMap = new HashMap<String, String>();
					String[] pathArray = requestArray[1].split("\\?");
            		String[] paramsArray = pathArray[1].split("&");
            		for (String s : paramsArray) {
            			String[] split = s.split("=");
            			if (split.length == 2) {
            				paramMap.put(split[0], split[1]);	
            			}
            		}
					if (paramMap.containsKey("state")) {
						if (requestArray[0].equals("GET")) {
							if (pathArray[0].equals("/")) {
								if (paramMap.containsKey("code")) {
									// zoom sent a code to this server
									clientCodes.put(paramMap.get("state"), paramMap.get("code"));
			                    	bufferedWriter.write("HTTP/1.1 200 OK\r\n"
			                    			+ "Content-Type: text/plain"
			                    			+ "\r\n\r\n");
			                    	bufferedWriter.write("insert thank you page here");
			                    	bufferedWriter.flush();
								}
							} else if (pathArray[0].equals("/auth")) {
								// client needs to generate an access code
		                    	bufferedWriter.write("HTTP/1.1 302 Found\r\n"
		                    			+ "Location: https://zoom.us/oauth/authorize"
		                    			+ "?response_type=code"
		                    			+ "&client_id=" + clientID
		                    			+ "&redirect_uri=" + redirectURL
		                    			+ "&state=" + paramMap.get("state")
		                    			+ "\r\n\r\n");
		                    	bufferedWriter.flush();
							} else if (pathArray[0].equals("/code")) {
								if (clientCodes.containsKey(paramMap.get("state"))) {
									// client requests their access code
			                    	bufferedWriter.write("HTTP/1.1 200 OK\r\n"
			                    			+ "Content-Type: text/plain"
			                    			+ "\r\n\r\n");
			                    	bufferedWriter.write(clientCodes.get(paramMap.get("state")));
			                    	bufferedWriter.flush();
								}
							} else if (pathArray[0].equals("/token")) {
								if (paramMap.containsKey("code")) {
									// client requests their access token
									
									// generate encoded code
									String basic = clientID + ":" + clientSecret;
			                        String encoded = Base64.getEncoder().encodeToString(basic.getBytes());
			                        String body = "grant_type=authorization_code&code=" + paramMap.get("code") + "&redirect_uri=" + redirectURL;
			                        
			                        // build POST request
			                        HttpRequest httpRequest = HttpRequest.newBuilder()
			                                .uri(URI.create("https://zoom.us/oauth/token"))
			                                .setHeader("Authorization", "Basic " + encoded)
			                                .setHeader("Content-Type", "application/x-www-form-urlencoded")
			                                .POST(HttpRequest.BodyPublishers.ofString(body))
			                                .build();
			                        
			                        // send the request to zoom
			                        HttpClient httpClient = HttpClient.newHttpClient();
			                        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

			                        // send zoom's response to requesting client
			                    	bufferedWriter.write("HTTP/1.1 200 OK" + "\r\n"
			                    			+ "Content-Type: text/plain" + "\r\n"
			                    			+ "\r\n");
			                    	bufferedWriter.write(response.body());
			                    	bufferedWriter.flush();	
								}
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}			
		public void close() {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new OAuthServer(55556);
	}
}