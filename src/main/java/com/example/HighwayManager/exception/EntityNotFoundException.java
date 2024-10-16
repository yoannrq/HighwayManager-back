package com.example.HighwayManager.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " non trouvé avec l'ID : " + id,"ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
