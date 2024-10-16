package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.Status;
import com.example.HighwayManager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param status as an object status
     * @return the status object saved
     * @throws IllegalStateException if name is already used
     */
    @PostMapping("/status")
    public Status createStatus(@RequestBody Status status) {
        //Verify if status name is not already used in database
        Optional<Status> isStatusNameExist = statusService.getStatusByName(status.getName());
        if (isStatusNameExist.isPresent()) {
            throw new IllegalArgumentException("Ce nom de status est déjà utilisé");
        }

        return statusService.saveStatus(status);
    }

    /**
     * Read - Get on status by id
     * @param id - The id of the status
     * @return status || null
     */
    @GetMapping("/status/{id}")
    public Status getStatus(@PathVariable final long id) {
        Optional<Status> status = statusService.getStatusById(id);
        return status.orElse(null);
    }

    /**
     * Read - Get all status
     * @return - An iterable object of Status
     */
    @GetMapping("/status")
    public Iterable<Status> getAllStatus() {
        return statusService.getAllStatus();
    }

    /**
     * Patch - Update an existing status
     * @param id - the id of the status to update
     * @param statusBody - The status object to update
     * @return status ||null - The status object updated
     * @throws IllegalStateException if status name is already used
     */
    @PatchMapping("/status/{id}")
    public Status updateStatus(@PathVariable final long id, @RequestBody Status statusBody) {
        Optional<Status> statusInDatabase = statusService.getStatusById(id);
        if (statusInDatabase.isPresent()) {
            Status statusToUpdate = statusInDatabase.get();

            String statusName = statusBody.getName();
            if (statusName != null && !statusName.isEmpty()) {
                //Verify if status name is not already used in database
                Optional<Status> isStatusNameExist = statusService.getStatusByName(statusName);
                if (isStatusNameExist.isEmpty()) {
                    statusToUpdate.setName(statusName);
                } else {
                    throw new IllegalStateException("Ce nom de status est déjà utilisé");
                }
            }

            return statusService.saveStatus(statusToUpdate);
        } else {
            return null;
        }
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
