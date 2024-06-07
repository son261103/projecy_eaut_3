package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long>{
    Users findByUsername(String username);
}
