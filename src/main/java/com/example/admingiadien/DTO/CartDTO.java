package com.example.admingiadien.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;

}
