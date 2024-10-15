package com.example.HighwayManager.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " non trouvé avec l'ID : " + id);
    }
}
