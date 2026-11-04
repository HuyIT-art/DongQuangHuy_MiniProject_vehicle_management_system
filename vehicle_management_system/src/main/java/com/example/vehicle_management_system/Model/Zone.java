package com.example.vehicle_management_system.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "zones")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long capacity; //suc chua toi da
    private Long occupiedSpots; //số chỗ đã dung

    @OneToMany(mappedBy = "zone")
    private List<ParkingTicket> parkingTickets;
}
