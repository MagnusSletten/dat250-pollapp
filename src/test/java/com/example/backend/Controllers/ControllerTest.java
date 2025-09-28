package com.example.backend.Controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.example.backend.Model.PollManager;
import com.example.backend.Model.Poll.User.User;
import com.example.backend.Repositories.PollRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Repositories.VoteRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) 

class ControllerTest {


    @LocalServerPort int port;
    @Autowired
    UserRepository userRepository; 
    @Autowired
    PollRepository pollRepository; 
    @Autowired
    VoteRepository voteRepository; 
    @Autowired PollManager pollManager;
    RestClient client; 



    @BeforeEach
    void setUp() {
        client = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
        
    }

    @Test
    void createPollTest() throws JSONException{
        sendUser();
        var resp = createPoll(getPollRequest1());
        assertTrue(resp.getBody().contains("id")); 
    }

    @Test
    void getPollTest() throws JSONException {
        sendUser();
        var resp = createPoll(getPollRequest1());
        ResponseEntity<String> pollReturn = getPoll1();
        JSONObject actual = new JSONObject(pollReturn.getBody());
        assertTrue(actual.has("id"));
        JSONAssert.assertEquals(getExpectedPoll_1(), actual,true);  
          
    }

    @Test
    void deletePoll()throws JSONException{
        sendUser();
        var resp1 = createPoll(getPollRequest1());
        ResponseEntity<String> pollReturn = createPoll(getPollRequest1());
        assertNotNull(pollReturn);
        ResponseEntity<String> deleteResponse = client.delete()
        .uri("/polls/1")
        .retrieve()
        .toEntity(String.class);
        ResponseEntity resp = getPoll1();
        assertNull((resp.getBody())); 



    }

    @Test 
    void testSequence() throws JSONException{
        sendUser();
        var resp = createPoll(getPollRequest1());
        ResponseEntity<String> usersReturn= getUsers();
        assert(usersReturn.getBody().contains("Andrew"));
        sendUser2();
        ResponseEntity<String> usersReturn2= getUsers();
        assert(usersReturn2.getBody().contains("Andrew"));
        assert(usersReturn2.getBody().contains("Peter"));
        ResponseEntity<String> poll =  createPoll(getPollRequest1());
        JSONObject pollJSON = new JSONObject(poll.getBody());
        Integer pollId = pollJSON.getInt("id");
        sendVote(1,pollId);
        sendVote(2,pollId);
        ResponseEntity<String> votes = getVotes(pollId);
        JSONArray arr = new JSONArray(votes.getBody());
        assertEquals(1, arr.length());
        client.delete()
        .uri("/polls/" + pollId)
        .retrieve()
        .toEntity(String.class);
        
        assertThrows(HttpServerErrorException.class, () -> {
    getVotes(pollId);
});
        

                

    }


    ResponseEntity<String> getUsers(){
    ResponseEntity<String> response = client.get()
    .uri("/users")
    .retrieve()
    .toEntity(String.class);
    return response; 

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
        pollJSON.put("id",1);
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
        pollJSON.put("createdBy","Andrew");

        return pollJSON;
    }

    public void sendUser() throws JSONException{
        JSONObject user = new JSONObject();
        user.put("username", "Andrew");

        var userResp = client.post().uri("/users")
        .contentType(APPLICATION_JSON)
        .body(user.toString())
        .retrieve()
        .toEntity(User.class);

    }

        public void sendUser2() throws JSONException{
        JSONObject user = new JSONObject();
        user.put("username", "Peter");

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



    ResponseEntity<String> getVotes(Integer pollId) throws JSONException {
    ResponseEntity<String> response = client.get()
    .uri("/polls/" +pollId +"/votes")
    .retrieve()
    .toEntity(String.class);

    return response; 
}

JSONObject getVote(Integer presentationOrder,Integer pollId) throws JSONException{
        JSONObject voteJSON = new JSONObject();
        voteJSON.put("pollId", pollId);
        voteJSON.put("presentationOrder", presentationOrder);
        voteJSON.put("username", "Andrew");
        return voteJSON;
    }


    public ResponseEntity<String> sendVote(Integer presentationOrder, Integer pollId) throws JSONException{
        ResponseEntity<String> voteRes = client.post().uri("/polls/" + pollId + "/votes")
        .contentType(APPLICATION_JSON)
        .body(getVote(presentationOrder,pollId).toString())
        .retrieve()
        .toEntity(String.class);
        return voteRes;


    }


}
