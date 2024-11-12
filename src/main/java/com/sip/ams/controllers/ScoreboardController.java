package com.sip.ams.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoreboardController { // a controller to handle messages sent to the server. This will listen for
									// messages from clients and then broadcast responses.

	@GetMapping("/scoreboard/")
    public String scoreboardChat(Model model) { 
        return "pages/scoreboard";
    }		
	
	/*
	 * Messages sent to /app/scoreboard are routed to the getScore() method.
	 * The @SendTo annotation broadcasts the response to all subscribers of /topic/scoreboard. 
	 */
	
	
	// Handles messages sent to "/app/scoreboard"
	@MessageMapping("/scoreboard")
	@SendTo("/topic/scoreboard")
	public String getScore(String message) {
		System.out.println("Received message from client: " + message);
		// Broadcast a response
		return "The score for yesterday's game is 3-1";
	}
}
