package com.example.admingiadien.Mapper;

import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper implements EntityMapper<Products, ProductsDTO> {

    @Override
    public Products toEntity(ProductsDTO dto) {
        return Products.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .isactive(dto.getIsactive())
                .image(dto.getImage())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public ProductsDTO toDto(Products entity) {
        return ProductsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .isactive(entity.getIsactive())
                .image(entity.getImage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<Products> toEntity(List<ProductsDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsDTO> toDto(List<Products> entity) {
        return entity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
