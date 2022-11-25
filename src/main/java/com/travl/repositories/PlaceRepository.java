package com.travl.repositories;

import com.travl.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findById(Long id);
    List<Place> findAllByProposalId(Long proposalId);
}
