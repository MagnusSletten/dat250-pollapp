package com.example.backend.Model.Poll;
import lombok.Data;
import java.time.Instant;
@Data
public class Vote {
    private Instant publishedAt = Instant.now();
    Integer userId;
    Integer pollId;
    Integer optionId; 
 
    
}
