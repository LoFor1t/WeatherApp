package com.LoFor1t.WeatherApp.repositories;

import com.LoFor1t.WeatherApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLogin(String login);
    Optional<User> getUserByLogin(String login);
}
