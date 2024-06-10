package com.example.admingiadien.Mapper;

import com.example.admingiadien.DTO.CartDTO;
import com.example.admingiadien.Entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper implements EntityMapper<Cart, CartDTO> {
    @Override
    public Cart toEntity(CartDTO dto) {
        return Cart.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    @Override
    public CartDTO toDto(Cart entity) {
        return CartDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public List<Cart> toEntity(List<CartDTO> dtos) {
        List<Cart> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(toEntity(dto)));
        return entities;
    }

    @Override
    public List<CartDTO> toDto(List<Cart> entities) {
        List<CartDTO> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }
}
