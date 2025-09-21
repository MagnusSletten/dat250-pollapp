package com.example.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.Model.Poll.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    List<Poll> findByTitleContainingIgnoreCase(String titlePart);
}