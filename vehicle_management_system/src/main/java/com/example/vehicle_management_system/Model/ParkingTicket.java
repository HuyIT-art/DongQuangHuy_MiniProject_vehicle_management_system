package com.example.vehicle_management_system.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parking_tickets")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime; // thoi gian xe vao

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime; // thoi gian xe ra

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;
}
