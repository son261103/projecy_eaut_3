package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
