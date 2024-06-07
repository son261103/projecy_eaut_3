package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.PaymentMethodsDTO;
import com.example.admingiadien.Service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    // hiển thị danh mục thanh toán
    @GetMapping("/admin/paymentmethod")
    public String showFormPaymentMethod(Model model){
        List<PaymentMethodsDTO> paymentMethodsDTOS = paymentMethodService.showAllPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethodsDTOS);
        return "Admin/pages/payment/payment";
    }

    //thêm danh mục thanh toán
    @GetMapping("/admin/addpaymentmethod/add")
    public String showFormAddPaymentMethod(Model model){
        model.addAttribute("paymentMethod" , new PaymentMethodsDTO());
        return "Admin/pages/payment/addpaymentmethod";
    }

    @PostMapping("/admin/addpaymentmethod/add")
    public String addPaymentMethod(@ModelAttribute("paymentMethod") PaymentMethodsDTO paymentMethodsDTO, RedirectAttributes redirectAttributes){
        try {
            paymentMethodService.addPaymentMethod(paymentMethodsDTO);
            redirectAttributes.addFlashAttribute("successMessage" , "Thêm thanh toán thành công!");
            return "redirect:/admin/paymentmethod";
        }catch (RuntimeException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage" ,"Failed to add addpaymentmethod: " + e.getMessage());
            return  "redirect:/admin/addpaymentmethod";
        }
    }
}
