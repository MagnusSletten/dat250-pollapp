package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.backend.Controllers.PollController;
import com.example.backend.Controllers.UserController;
import com.example.backend.Controllers.VoteController;
import com.example.backend.Model.PollManager;
import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.VoteOption;
import com.example.backend.Model.Poll.User.User;
import com.example.backend.Model.Poll.Vote.Vote;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import redis.clients.jedis.UnifiedJedis;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);



	}

}
