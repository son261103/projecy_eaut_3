package com.example.admingiadien.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
    private Long id;
    private String name;
    private Set<UsersDTO> users;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
