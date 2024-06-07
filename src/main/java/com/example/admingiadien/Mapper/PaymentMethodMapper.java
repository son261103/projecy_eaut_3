package com.example.admingiadien.Mapper;

import com.example.admingiadien.DTO.PaymentMethodsDTO;
import com.example.admingiadien.Entity.PaymentMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMethodMapper implements EntityMapper<PaymentMethods, PaymentMethodsDTO> {
    @Override
    public PaymentMethods toEntity(PaymentMethodsDTO dto) {
        return PaymentMethods
                .builder()
                .id(dto.getId())
                .type(dto.getType())
                .provider(dto.getProvider())
                .accountNo(dto.getAccountNo())
                .expiryDate(dto.getExpiryDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public PaymentMethodsDTO toDto(PaymentMethods entity) {
        return PaymentMethodsDTO
                .builder()
                .id(entity.getId())
                .userId(entity.getId())
                .type(entity.getType())
                .provider(entity.getProvider())
                .accountNo(entity.getAccountNo())
                .expiryDate(entity.getExpiryDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<PaymentMethods> toEntity(List<PaymentMethodsDTO> Dto) {
        return List.of();
    }

    @Override
    public List<PaymentMethodsDTO> toDto(List<PaymentMethods> entity) {
        List<PaymentMethodsDTO> dtos = new ArrayList<>();
        entity.forEach(PaymentMethods ->
                {
                    PaymentMethodsDTO paymentMethodsDTO = toDto(PaymentMethods);
                    dtos.add(paymentMethodsDTO);
                }
        );
        return dtos;
    }
}
