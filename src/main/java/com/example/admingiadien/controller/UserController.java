package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.UsersDTO;
import com.example.admingiadien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginUser", new UsersDTO());
        model.addAttribute("registerUser", new UsersDTO());
        return "Users/pages/login";
    }

    @GetMapping("/admin")
    public String defaultAfterLogin() {
        return "redirect:/admin/index";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute("loginUser") UsersDTO userDTO, Model model) {
        // Thực hiện xác thực thông qua UserService
        if (userService.authenticate(userDTO.getUsername(), userDTO.getPassword())) {
            // Kiểm tra xem tài khoản có phải là admin không và chuyển hướng tương ứng
            if (userService.isAdmin(userDTO.getUsername())) {
                return "redirect:/admin/index";
            } else {
                return "redirect:/index";
            }
        } else {
            // Xử lý trường hợp đăng nhập không thành công
            return "redirect:/users/login";
        }
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("registerUser") UsersDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Users/pages/login";
        }
        userService.saveUser(userDTO);
        return "redirect:/users/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("loginUser", new UsersDTO());
        return "Admin/pages/loginAdmin";
    }

    @PostMapping("/admin/registerAdmin")
    public String registerAdmin(@RequestBody UsersDTO userDTO) {
        // Xử lý đăng ký admin ở đây
        userService.saveAdmin(userDTO);
        return "redirect:/admin/index";
    }


}
