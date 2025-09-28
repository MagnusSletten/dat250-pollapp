package com.example.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.User.User;
import com.example.backend.Model.Poll.Vote.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByPollId(Integer pollId);
    List<Vote> findAllByVoter(User voter);
    Vote findByPollIdAndVoterId(Integer pollId, Integer voterId);
    boolean existsByPollIdAndVoterId(Integer pollId, Integer voterId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    int deleteByPollId(@Param("pollId") Integer pollId);
}
