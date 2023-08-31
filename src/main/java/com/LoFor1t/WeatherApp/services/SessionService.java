package com.LoFor1t.WeatherApp.services;

import com.LoFor1t.WeatherApp.entities.Session;
import com.LoFor1t.WeatherApp.entities.User;
import com.LoFor1t.WeatherApp.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSessionById(String sessionId) {
        if (sessionId.isEmpty()) {
            return null;
        }

        Optional<Session> session = sessionRepository.findById(sessionId);

        return session.orElse(null);

    }

    public Integer getUserIdBySession(String sessionId) {
        Session session = getSessionById(sessionId);

        if (session == null) {
            return null;
        }

        return session.getUser().getId();
    }

    public User getUserBySession(String sessionId) {
        Session session = getSessionById(sessionId);

        if (session == null) {
            return null;
        }

        return session.getUser();
    }
}
