package com.example.vehicle_management_system.ExceptionCenter;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
