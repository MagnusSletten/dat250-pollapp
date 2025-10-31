package com.example.backend.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend.Model.Poll.BasicPollInfoDto;
import com.example.backend.Model.Poll.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    List<Poll> findByTitleContainingIgnoreCase(String titlePart);

    Optional<Poll> findTopByOrderByIdDesc();

    List<Poll> findByCreatedByUsername(String username);

    @Query("""
    select p.id as id, p.title as title, u.username as creatorName
    from Poll p join p.createdBy u
    order by p.publishedAt desc, p.id desc
    """)
    List<BasicPollInfoDto> findBasicPollInfo();;
    }

    
