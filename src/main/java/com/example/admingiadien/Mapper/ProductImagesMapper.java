package com.example.admingiadien.Mapper;

import com.example.admingiadien.DTO.ProductImagesDTO;
import com.example.admingiadien.Entity.ProductImages;
import com.example.admingiadien.Entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductImagesMapper implements EntityMapper<ProductImages, ProductImagesDTO> {

    @Override
    public ProductImages toEntity(ProductImagesDTO dto) {
        return ProductImages
                .builder()
                .id(dto.getId())
                .products(Products.builder().id(dto.getProductId()).build())
                .imageUrl(dto.getImageUrl())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public ProductImagesDTO toDto(ProductImages entity) {
        return ProductImagesDTO
                .builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<ProductImages> toEntity(List<ProductImagesDTO> Dto) {
        return List.of();
    }

    @Override
    public List<ProductImagesDTO> toDto(List<ProductImages> entity) {
        List<ProductImagesDTO> dtos = new ArrayList<>();
        entity.forEach(ProductImages ->
                {
                    ProductImagesDTO productImagesDTO = toDto(ProductImages);
                    dtos.add(productImagesDTO);
                }
        );
        return dtos;
    }
}
