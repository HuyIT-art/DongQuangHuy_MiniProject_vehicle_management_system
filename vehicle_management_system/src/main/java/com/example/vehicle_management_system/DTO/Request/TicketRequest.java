package com.example.vehicle_management_system.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    private Long vehicleId;
    private Long zoneId;
}
