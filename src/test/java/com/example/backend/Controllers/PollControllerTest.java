package com.example.backend.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PollControllerIT {

    String pollJSON = """
    {
      "title": "Pizza preference",
      "question": "Do you hate pizza?",
      "voteOptions": [
        { "caption": "yes" },
        { "caption": "no" }
      ]
    }
    """;

    @LocalServerPort int port;
    RestClient client;

    @BeforeEach
    void setUp() {
        client = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void createPollTest(){
        var resp = createPoll();
        assertTrue(resp.getBody().contains("Succesfully")); 
    }

    @Test
    void getPollTest(){
        createPoll();
        String pollReturn = client.get().uri("/polls/1").retrieve() 
	    .body(String.class);
        assertTrue(pollReturn.contains("Pizza preference"));  
    }

  
    ResponseEntity<String> createPoll() {
        var resp = client.post()
                .uri("/polls")   
                .contentType(APPLICATION_JSON)
                .body(pollJSON)
                .retrieve()
                .toEntity(String.class);
        return resp;
    }


}
