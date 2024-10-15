package com.example.HighwayManager.repository;

import com.example.HighwayManager.model.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, Long> {
    Optional<EventType> findByName(String name);
}
