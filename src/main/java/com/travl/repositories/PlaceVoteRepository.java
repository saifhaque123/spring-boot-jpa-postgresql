package com.travl.repositories;

import com.travl.models.PlaceVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceVoteRepository extends JpaRepository<PlaceVote, Long> {
    Optional<PlaceVote> findById(Long id);
    List<PlaceVote> findAllByPlaceId(Long placeId);
}
