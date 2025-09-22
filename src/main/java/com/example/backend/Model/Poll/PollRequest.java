package com.example.backend.Model.Poll;

import java.time.Instant;
import java.util.ArrayList;


import com.example.backend.Model.User;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PollRequest {
    private String title;
    private String question;
    private ArrayList<VoteOption> voteOptions = new ArrayList<>();
    private Instant publishedAt;
    private Instant validUntil;
    private String creator;

    public Poll toPoll(User creator){
    Poll poll = new Poll();
    poll.setCreatedBy(creator);
    poll.setQuestion(question);
    poll.setOptions(voteOptions);
    poll.setTitle(title);
    poll.setValidUntil(validUntil);
    poll.setPublishedAt(publishedAt);
    return poll; 
    }

}

