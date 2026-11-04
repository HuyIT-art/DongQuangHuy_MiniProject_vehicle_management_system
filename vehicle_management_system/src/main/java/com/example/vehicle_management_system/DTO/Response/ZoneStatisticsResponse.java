package com.example.vehicle_management_system.DTO.Response;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneStatisticsResponse {
    private Long id;
    private String name;
    private Long capacity;
    private Long occupiedSlots;
    private Long availableSlots;

}
