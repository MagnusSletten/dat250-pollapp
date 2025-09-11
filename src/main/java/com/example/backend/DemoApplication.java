package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.backend.Controllers.PollController;
import com.example.backend.Controllers.UserController;
import com.example.backend.Controllers.VoteController;
import com.example.backend.Model.PollManager;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		PollManager pollManager = new PollManager(); 
		PollController PollController = new PollController(pollManager);
		UserController userController = new UserController(pollManager);
		VoteController voteController = new VoteController(pollManager); 
		
	}

}
