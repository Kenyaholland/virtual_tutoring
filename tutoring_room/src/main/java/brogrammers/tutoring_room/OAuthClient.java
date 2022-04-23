package brogrammers.tutoring_room;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

public class OAuthClient {
	// this code will redirect the client to the redirect URL
	// zoom will send an access code to OAuth server after the client authorizes
	@SuppressWarnings("exports")
	public static void GenerateCode(HttpClient client, String state) throws IOException, InterruptedException, URISyntaxException {     
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:55556/auth" + "?state=" + state))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, List<String>> header = response.headers().map();
        
        if (header.containsKey("Location")) {
        	String url = header.get("Location").get(0);
        	Desktop.getDesktop().browse(new URL(url).toURI());		
        }
	}
	// send a request to the OAuth server asking for the generated code
	@SuppressWarnings("exports")
	public static String RequestCode(HttpClient client, String state) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:55556/code" + "?state=" + state))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
	}
	// send a request to the OAuth server asking for access token
	@SuppressWarnings("exports")
	public static String RequestToken(HttpClient client, String state, String code) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:55556/token" + "?state=" + state + "&code=" + code))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
	}
	@SuppressWarnings("exports")
	public static String RequestUserInformation(HttpClient client, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
        		.GET()
                .uri(URI.create("https://api.zoom.us/v2/users/me"))
                .setHeader("Authorization", "Bearer " + token)
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
	}
	
	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
	
	@SuppressWarnings("exports")
	public static String CreateMeeting(HttpClient client, String token) throws IOException, InterruptedException {
		
		Map<Object, Object> data = new HashMap<>();
		data.put("userId", "kenyamholland@gmail.com");
		data.put("agenda", "TUTORING");
        data.put("pre_schedule", "false");
        data.put("type", "1");
		
        String apiUrl = "https://api.zoom.us/v2/users/me/meetings";

        HttpRequest request = HttpRequest.newBuilder()
        		.POST(buildFormDataFromMap(data))
                .uri(URI.create(apiUrl))
                .setHeader("Authorization", "Bearer " + token)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(new JSONObject(response.body()).toString(4));
        return response.body();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {	
		
		// this main function is for testing the HTTP functions
		
		// generate client identifier
		HttpClient client = HttpClient.newHttpClient();
		String state = Base64.getEncoder().encodeToString(("kmh99" + ":" + new Random().nextInt(999999)).getBytes());
		
		// send client to redirect URL
		GenerateCode(client, state);
		
		// this is for testing
		// we can check if there is a generated code by calling RequestCode()
        TimeUnit.SECONDS.sleep(5);
        
        // request access code
        String code = RequestCode(client, state);
        
        // get access token
        String token = RequestToken(client, state, code);
                
        // token is used to make HTTP requests to zoom API
        String data = RequestUserInformation(client, new JSONObject(token).get("access_token").toString());
        System.out.println(new JSONObject(data).toString(4));
	}
}
