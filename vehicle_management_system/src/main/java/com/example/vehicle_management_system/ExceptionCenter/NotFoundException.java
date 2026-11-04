package com.example.vehicle_management_system.ExceptionCenter;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
