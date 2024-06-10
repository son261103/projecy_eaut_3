package com.example.admingiadien.DTO;

import com.example.admingiadien.Entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Boolean isactive;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageDescription;
    private List<ProductImagesDTO> images;
}
