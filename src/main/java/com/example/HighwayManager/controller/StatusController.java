package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.StatusCreationDTO;
import com.example.HighwayManager.dto.StatusDTO;
import com.example.HighwayManager.model.Status;
import com.example.HighwayManager.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.HighwayManager.exception.IllegalStateException;
import com.example.HighwayManager.exception.IllegalArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StatusController {
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Create - Add a new status
     * @param statusBody as an object status
     * @return the status object saved
     * @throws IllegalArgumentException if name is already used
     */
    @PostMapping("/status")
    public StatusDTO createStatus(@Valid @RequestBody StatusCreationDTO statusBody) {
        //Verify if status name is not already used in database
        Optional<Status> isStatusNameExist = statusService.getStatusByName(statusBody.getName());
        if (isStatusNameExist.isPresent()) {
            throw new IllegalArgumentException("Ce nom de status est déjà utilisé");
        }

        Status status = new Status();
        status.setName(statusBody.getName());
        Status savedStatus = statusService.saveStatus(status);
        return new StatusDTO(savedStatus);
    }

    /**
     * Read - Get on status by id
     * @param id - The id of the status
     * @return status
     * @throws IllegalArgumentException Status not found
     */
    @GetMapping("/status/{id}")
    public StatusDTO getStatus(@PathVariable final long id) {
        Optional<Status> status = statusService.getStatusById(id);
        if (status.isEmpty()) {
            throw new IllegalArgumentException("Statut non trouvé");
        } else {
            return new StatusDTO(status.get());
        }
    }

    /**
     * Read - Get all status
     * @return - A list of Status
     */
    @GetMapping("/status")
    public List<StatusDTO> getAllStatus() {
        Iterable<Status> allStatus = statusService.getAllStatus();
        List<StatusDTO> statusList = new ArrayList<>();
        for (Status status : allStatus) {
            statusList.add(new StatusDTO(status));
        }
        return statusList;
    }

    /**
     * Patch - Update an existing status
     * @param id - the id of the status to update
     * @param statusBody - The status object to update
     * @return status - The status object updated
     * @throws IllegalStateException if status name is already used
     */
    @PatchMapping("/status/{id}")
    public StatusDTO updateStatus(@PathVariable final long id, @Valid @RequestBody StatusCreationDTO statusBody) {
        Optional<Status> statusInDatabase = statusService.getStatusById(id);

        if (statusInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Statut introuvable");
        }

        Status statusToUpdate = statusInDatabase.get();

        String statusName = statusBody.getName();
        if (statusName != null && !statusName.isEmpty()) {
            //Verify if status name is not already used in database
            Optional<Status> isStatusNameExist = statusService.getStatusByName(statusName);
            if (isStatusNameExist.isEmpty()) {
                statusToUpdate.setName(statusName);
            } else {
                throw new IllegalStateException("Ce nom de statut est déjà utilisé");
            }
        }

        Status updatedStatus = statusService.saveStatus(statusToUpdate);
        return new StatusDTO(updatedStatus);
    }

    /**
     * Delete - Delete a status
     * @param id - The id of the status to delete
     */
    @DeleteMapping("/status/{id}")
    public void deleteStatus(@PathVariable final long id) {
        statusService.deleteStatus(id);
    }
}
