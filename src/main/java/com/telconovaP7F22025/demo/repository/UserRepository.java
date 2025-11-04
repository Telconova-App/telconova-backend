package com.telconovaP7F22025.demo.repository;
import com.telconovaP7F22025.demo.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailUsuario(String emailUsuario);
    boolean existsByEmailUsuario(String emailUsuario);
    
}
