package com.example.admingiadien.Mapper;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.Entity.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesMapper implements EntityMapper<Categories, CategoriesDTO> {
    @Override
    public Categories toEntity(CategoriesDTO dto) {
        return Categories
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public CategoriesDTO toDto(Categories entity) {
        return CategoriesDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<Categories> toEntity(List<CategoriesDTO> Dto) {
        return List.of();
    }

    @Override
    public List<CategoriesDTO> toDto(List<Categories> entity) {
        List<CategoriesDTO> dtos = new ArrayList<>();
        entity.forEach(Categori ->
                {
                    CategoriesDTO categoriesDTO = toDto(Categori);
                    dtos.add(categoriesDTO);
                }
                );
        return dtos;
    }
}
