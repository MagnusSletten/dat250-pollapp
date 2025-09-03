package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.PollOption;

import lombok.Data;

@Data
public class Poll {
    private Integer pollID; 
    private String title; 
    private PollQuestion question; 
    
}
