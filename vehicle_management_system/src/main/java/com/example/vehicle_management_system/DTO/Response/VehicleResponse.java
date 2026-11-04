package com.example.vehicle_management_system.DTO.Response;
import com.example.vehicle_management_system.Enum.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    private Long id;
    private String licensePlate;
    private String color;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
