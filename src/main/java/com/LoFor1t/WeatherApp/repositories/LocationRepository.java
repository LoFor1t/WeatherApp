package com.LoFor1t.WeatherApp.repositories;

import com.LoFor1t.WeatherApp.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Iterable<Location> findByUserId(int id);
}
