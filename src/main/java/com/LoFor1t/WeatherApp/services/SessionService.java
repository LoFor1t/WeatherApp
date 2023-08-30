package com.LoFor1t.WeatherApp.services;

import com.LoFor1t.WeatherApp.entities.Session;
import com.LoFor1t.WeatherApp.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Integer getUserIdBySession(String sessionId) {
        if (sessionId.isEmpty()) {
            return null;
        }

        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isEmpty()) {
            return null;
        }

        return session.get().getUser().getId();

    }
}
