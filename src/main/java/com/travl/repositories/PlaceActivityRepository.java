package com.travl.repositories;

import com.travl.models.PlaceActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceActivityRepository extends JpaRepository<PlaceActivity, Long> {
        Optional<List<PlaceActivity>> findByPlaceId(Long id);
        List<PlaceActivity> findAllByPlaceId(Long id);
}

