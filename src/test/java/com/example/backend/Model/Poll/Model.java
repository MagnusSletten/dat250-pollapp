package com.example.backend.Model.Poll;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class Model {
    
	@Test
	void PollCreation(){
		Poll poll = new Poll();
        poll.setPollID(1);
        assert(poll.getPollID().equals(1));
	}
    
    
}
