package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;

public interface AutService {
    boolean authenticateUser(LoginRequest loginRequest);

    /**
     * Register a new user. Returns true if registration succeeded, false if a user with the same email already exists.
     */
    boolean registerUser(RegisterRequest registerRequest);
}

