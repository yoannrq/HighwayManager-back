package com.example.HighwayManager.service;

import com.example.HighwayManager.model.Status;
import com.example.HighwayManager.repository.StatusRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Optional<Status> getStatusById(final long id) {
        return statusRepository.findById(id);
    }

    public Optional<Status> getStatusByName(final String name) {
        return statusRepository.findByName(name);
    }

    public Iterable<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    public void deleteStatus(final long id) {
        statusRepository.deleteById(id);
    }

    public Status saveStatus(final Status status) {
        return statusRepository.save(status);
    }
}
