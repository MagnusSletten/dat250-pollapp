package com.example.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.Model.Poll.Vote.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByPollId(Integer pollId);
}
