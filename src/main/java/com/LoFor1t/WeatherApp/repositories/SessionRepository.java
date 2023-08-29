package com.LoFor1t.WeatherApp.repositories;

import com.LoFor1t.WeatherApp.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
}
