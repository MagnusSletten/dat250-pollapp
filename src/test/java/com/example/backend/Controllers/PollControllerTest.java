package com.example.backend.Controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) 

class PollControllerTest {


    @LocalServerPort int port;
    RestClient client; 

    @BeforeAll
    void setUp() {
        client = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
        
    }

    @Test
    void createPollTest() throws JSONException{
        sendUser();
        var resp = createPoll(getPollRequest1());
        assertTrue(resp.getBody().contains("pollID")); 
    }

    @Test
    void getPollTest() throws JSONException {
        sendUser();
        ResponseEntity<String> pollReturn = createPoll(getPollRequest1());
        JSONObject actual = new JSONObject(pollReturn.getBody());
        assertTrue(actual.has("pollID"));
        JSONAssert.assertEquals(getExpectedPoll_1(), actual,true);  
          
    }

    @Test
    void deletePoll()throws JSONException{
        sendUser();
        ResponseEntity<String> pollReturn = createPoll(getPollRequest1());
        assertNotNull(pollReturn);
        ResponseEntity<String> deleteResponse = client.delete()
        .uri("/polls/1")
        .retrieve()
        .toEntity(String.class);
        ResponseEntity resp = getPoll1();
        assertNull((resp.getBody())); 



    }



  
    ResponseEntity<String> createPoll(JSONObject poll) throws JSONException {
        var resp = client.post()
        .uri("/polls")   
        .contentType(APPLICATION_JSON)
        .body(poll.toString())
        .retrieve()
        .toEntity(String.class);
        return resp;
    
        }
    JSONObject getExpectedPoll_1() throws JSONException{
        JSONObject pollJSON = getPollRequest1();
        pollJSON.put("pollID",1);
        pollJSON.put("votes", new JSONArray());
        pollJSON.put("publishedAt",JSONObject.NULL);
        pollJSON.put("validUntil",JSONObject.NULL);

        return pollJSON;
    }

      JSONObject getPollRequest1() throws JSONException{
        JSONObject pollJSON = new JSONObject();

        pollJSON.put("title", "Pizza preference");
        pollJSON.put("question", "Do you hate pizza?");

        JSONArray voteOptions = new JSONArray();
        voteOptions.put(new JSONObject()
            .put("caption", "yes")
            .put("presentationOrder", 1));
        voteOptions.put(new JSONObject()
            .put("caption", "no")
            .put("presentationOrder", 2));
        pollJSON.put("voteOptions", voteOptions);
        pollJSON.put("creator","Magnus");

        return pollJSON;
    }

    public void sendUser() throws JSONException{
        JSONObject user = new JSONObject();
        user.put("userName", "Magnus");

        var userResp = client.post().uri("/users")
        .contentType(APPLICATION_JSON)
        .body(user.toString())
        .retrieve()
        .toEntity(String.class);

    }

    ResponseEntity getPoll1() throws JSONException {
    ResponseEntity<String> response = client.get()
    .uri("/polls/1")
    .retrieve()
    .toEntity(String.class);

    return response; 
}


}
