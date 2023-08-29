package com.LoFor1t.WeatherApp.services;

import com.LoFor1t.WeatherApp.entities.Session;
import com.LoFor1t.WeatherApp.entities.User;
import com.LoFor1t.WeatherApp.repositories.SessionRepository;
import com.LoFor1t.WeatherApp.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignInService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public SignInService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public boolean isPasswordRight(String userLogin, String enteredPassword) {
        Optional<User> optionalUser = userRepository.getUserByLogin(userLogin);

        if (optionalUser.isEmpty()) {
            return false;
        }

        String userPassword = optionalUser.get().getPassword();
        return BCrypt.checkpw(enteredPassword, userPassword);
    }

    public boolean createSession(String userLogin, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.getUserByLogin(userLogin);

        if (optionalUser.isEmpty()) {
            return false;
        }

        String sessionId = UUID.randomUUID().toString();

        LocalDateTime expiredTime = LocalDateTime.now().plusHours(24);

        Session session = Session.builder()
                .id(sessionId)
                .user(optionalUser.get())
                .expiresAt(expiredTime)
                .build();

        sessionRepository.save(session);

        Cookie sessionCookie = new Cookie("sessionId", sessionId);

        response.addCookie(sessionCookie);

        return true;
    }
}
