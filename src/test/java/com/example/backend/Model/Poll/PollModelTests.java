package com.example.backend.Model.Poll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PollModelTests {

    @Test
    void pollCreation() {
        Poll poll = new Poll();
        poll.setPollID(1);
        assertEquals(1, poll.getPollID());
    }
}
