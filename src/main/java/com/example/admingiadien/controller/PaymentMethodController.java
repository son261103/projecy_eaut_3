package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.PaymentMethodsDTO;
import com.example.admingiadien.Service.PaymentMethodService;
import com.example.admingiadien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private UserService userService;

    // hiển thị danh mục thanh toán
    @GetMapping("/admin/paymentmethod")
    public String showFormPaymentMethod(Model model) {
        List<PaymentMethodsDTO> paymentMethodsDTOS = paymentMethodService.showAllPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethodsDTOS);
        return "Admin/pages/payment/payment";
    }

    //thêm danh mục thanh toán
    @GetMapping("/admin/addpaymentmethod")
    public String showFormAddPaymentMethod(Model model) {
        // Lấy thông tin người dùng đang đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName();
        Long currentUserId = userService.findByUsername(currentUserName).getId();

        // Thêm đối tượng PaymentMethodsDTO vào model để binding với form
        PaymentMethodsDTO paymentMethod = new PaymentMethodsDTO();
        paymentMethod.setUserId(currentUserId); // Đặt userId tự động
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("currentUserName", currentUserName);
        return "Admin/pages/payment/add_payment";
    }


    // Thêm phương thức thanh toán
    @PostMapping("/admin/addpaymentmethod/add")
    public String addPaymentMethod(@ModelAttribute("paymentMethod") PaymentMethodsDTO paymentMethodsDTO, RedirectAttributes redirectAttributes) {
        try {
            // Thực hiện thêm phương thức thanh toán và thông báo thành công
            paymentMethodService.addPaymentMethod(paymentMethodsDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thanh toán thành công!");
        } catch (RuntimeException e) {
            // Xử lý khi có lỗi xảy ra và thông báo cho người dùng
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add payment method: " + e.getMessage());
        }
        return "redirect:/admin/paymentmethod";
    }

    @GetMapping("/admin/payment-methods/{id}/edit")
    public String showEditPaymentMethodForm(@PathVariable("id") Long id, Model model) {
        // Lấy thông tin phương thức thanh toán dựa trên ID
        PaymentMethodsDTO paymentMethod = paymentMethodService.findPaymentMethodById(id);

        // Kiểm tra xem phương thức thanh toán có tồn tại hay không
        if (paymentMethod == null) {
            // Nếu không tìm thấy, chuyển hướng về trang danh sách phương thức thanh toán
            return "redirect:/admin/payment-methods";
        }

        // Thêm phương thức thanh toán vào model để binding với form
        model.addAttribute("paymentMethod", paymentMethod);

        // Lấy thông tin người dùng đang đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName();
        Long currentUserId = userService.findByUsername(currentUserName).getId();

        // Thêm thông tin người dùng vào model để hiển thị
        model.addAttribute("currentUserName", currentUserName);
        model.addAttribute("currentUserId", currentUserId);

        return "Admin/pages/payment/edit_payment";
    }

    @PostMapping("/admin/payment-methods/{id}/edit")
    public String editPaymentMethod(@PathVariable("id") Long id, @ModelAttribute("paymentMethod") PaymentMethodsDTO paymentMethodDTO, RedirectAttributes redirectAttributes) {
        try {
            // Thực hiện sửa phương thức thanh toán và thông báo thành công
            paymentMethodService.editPaymentMethod(paymentMethodDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Sửa phương thức thanh toán thành công!");
        } catch (RuntimeException e) {
            // Xử lý khi có lỗi xảy ra và thông báo cho người dùng
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to edit payment method: " + e.getMessage());
        }
        return "redirect:/admin/paymentmethod";
    }



    @PostMapping("/admin/payment-methods/{id}/delete")
    public String deletePaymentMethod(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Call the service method to delete the payment method by ID
            paymentMethodService.deletePaymentMethodById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa phương thức thanh toán thành công!");
        } catch (RuntimeException e) {
            // Handle any exceptions that might occur during deletion
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete payment method: " + e.getMessage());
        }
        return "redirect:/admin/paymentmethod";
    }


}
