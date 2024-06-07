package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.PaymentMethodsDTO;
import com.example.admingiadien.Entity.Categories;
import com.example.admingiadien.Entity.PaymentMethods;
import com.example.admingiadien.Mapper.PaymentMethodMapper;
import com.example.admingiadien.Repository.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    // hiển thị danh toán
    public List<PaymentMethodsDTO> showAllPaymentMethods(){
        List<PaymentMethods> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethodMapper.toDto(paymentMethods);
    }

    // thêm danh mục thanh toán
    @Transactional
    public PaymentMethodsDTO addPaymentMethod(PaymentMethodsDTO paymentMethodsDTO){
        paymentMethodsDTO.setCreatedAt(LocalDateTime.now());
        paymentMethodsDTO.setUpdatedAt(LocalDateTime.now());
        PaymentMethods paymentMethods = paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethodsDTO));
        return paymentMethodMapper.toDto(paymentMethods);
    }
}
