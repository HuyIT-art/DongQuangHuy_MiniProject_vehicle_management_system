package com.example.vehicle_management_system.Service;

import com.example.vehicle_management_system.DTO.Response.ZoneStatisticsResponse;
import com.example.vehicle_management_system.Model.Zone;
import com.example.vehicle_management_system.Repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;

    //Cach 1: Lay danh sach thong ke khu vuc
    public  List<ZoneStatisticsResponse> getAllZoneStatistics() {
        List<Zone> zones = zoneRepository.findAll();
        List<ZoneStatisticsResponse> list = zones.stream()
                .map(z -> new ZoneStatisticsResponse(
                        z.getId(),
                        z.getName(),
                        z.getCapacity(),
                        z.getOccupiedSpots(),
                        (z.getCapacity()-z.getOccupiedSpots())
                ))
                .toList();
        return list;
    }


    //Cach 2: Lay danh sach thong ke khu vuc
    public List<ZoneStatisticsResponse> getAllZone() {
        List<ZoneStatisticsResponse> list = zoneRepository.getZoneStatistics();
        return list;
    }
}
