package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.PaymentMethodsDTO;
import com.example.admingiadien.Entity.PaymentMethods;
import com.example.admingiadien.Entity.Users;
import com.example.admingiadien.Mapper.PaymentMethodMapper;
import com.example.admingiadien.Repository.PaymentMethodRepository;
import com.example.admingiadien.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    private final UserRepository userRepository;

    // Phương thức hiển thị tất cả phương thức thanh toán
    public List<PaymentMethodsDTO> showAllPaymentMethods() {
        List<PaymentMethods> paymentMethods = paymentMethodRepository.findAll();
        List<PaymentMethodsDTO> paymentMethodsDTOs = new ArrayList<>();

        for (PaymentMethods paymentMethod : paymentMethods) {
            PaymentMethodsDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

            // Lấy tên người dùng từ đối tượng người dùng và đặt vào trường userName trong PaymentMethodsDTO
            if (paymentMethod.getUser() != null) {
                String userName = paymentMethod.getUser().getUsername();
                paymentMethodDTO.setUserName(userName);
            }

            paymentMethodsDTOs.add(paymentMethodDTO);
        }

        return paymentMethodsDTOs;
    }

    // Phương thức thêm một phương thức thanh toán mới
    @Transactional
    public PaymentMethodsDTO addPaymentMethod(PaymentMethodsDTO paymentMethodsDTO) {
        // Thiết lập thời gian tạo và cập nhật
        paymentMethodsDTO.setCreatedAt(LocalDateTime.now());
        paymentMethodsDTO.setUpdatedAt(LocalDateTime.now());
        // Lưu phương thức thanh toán vào cơ sở dữ liệu
        PaymentMethods paymentMethods = paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethodsDTO));
        return paymentMethodMapper.toDto(paymentMethods);
    }

    // Phương thức tìm kiếm một phương thức thanh toán theo ID
    @Transactional(readOnly = true)
    public PaymentMethodsDTO findPaymentMethodById(Long id) {
        Optional<PaymentMethods> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethods paymentMethod = paymentMethodOptional.get();
            // Kiểm tra quyền truy cập của người dùng
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if (paymentMethod.getUser() != null && !username.equals(paymentMethod.getUser().getUsername())) {
                throw new RuntimeException("You don't have permission to access this payment method");
            }
            return paymentMethodMapper.toDto(paymentMethod);
        } else {
            throw new RuntimeException("Payment method not found with id: " + id);
        }
    }

    // Phương thức chỉnh sửa một phương thức thanh toán
    @Transactional
    public void editPaymentMethod(PaymentMethodsDTO paymentMethodsDTO) {
        // Lấy thông tin phương thức thanh toán cần chỉnh sửa
        PaymentMethods paymentMethod = paymentMethodRepository.findById(paymentMethodsDTO.getId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        // Chuyển đổi DTO thành đối tượng thực thể
        paymentMethod = paymentMethodMapper.toEntity(paymentMethodsDTO);
        // Thiết lập thời gian cập nhật
        paymentMethod.setUpdatedAt(LocalDateTime.now());
        // Lưu phương thức thanh toán vào cơ sở dữ liệu
        paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public void deletePaymentMethodById(Long id) {
        // Retrieve the payment method by ID
        PaymentMethodsDTO existingPaymentMethod = findPaymentMethodById(id);

        // Delete the payment method
        paymentMethodRepository.deleteById(id);
    }
}
