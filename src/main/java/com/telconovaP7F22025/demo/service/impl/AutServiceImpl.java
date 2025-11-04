package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.service.AutService;
import org.springframework.transaction.annotation.Transactional;
import com.telconovaP7F22025.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.telconovaP7F22025.demo.model.User;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AutServiceImpl implements AutService{
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Transactional(readOnly = true)
        public boolean authenticateUser(com.telconovaP7F22025.demo.dto.aut.LoginRequest loginRequest) {
            var userOpt = userRepository.findByEmailUsuario(loginRequest.email());
            if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.password(), userOpt.get().getPasswordUsuario())) {
                throw new RuntimeException("Invalid email or password");
            }
            return true;
        }

        @Transactional
        public boolean registerUser(RegisterRequest registerRequest) {
            String email = registerRequest.email();
            if (userRepository.existsByEmailUsuario(email)) {
                return false;
            }
            User u = new User();
            u.setEmailUsuario(email);
            u.setPasswordUsuario(passwordEncoder.encode(registerRequest.password()));
            u.setName(registerRequest.name());
            u.setRole(registerRequest.role());
            userRepository.save(u);
            return true;
        }
}
