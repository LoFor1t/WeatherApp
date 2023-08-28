package com.LoFor1t.WeatherApp.repositories;

import com.LoFor1t.WeatherApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
