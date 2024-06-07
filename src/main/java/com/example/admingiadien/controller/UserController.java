package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.UsersDTO;
import com.example.admingiadien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/users/login")
    public String login(@ModelAttribute("loginUser") UsersDTO userDTO, Model model) {
        // Đoạn code đã có

        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Kiểm tra nếu là ADMIN thì chuyển hướng đến trang đăng nhập của ADMIN
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/login";
        } else {
            // Ngược lại, chuyển hướng đến trang người dùng
            return "redirect:/index";
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


    @GetMapping("/admin/login")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("adminUser", new UsersDTO());
        return "Admin/pages/loginAdmin"; // Assuming you have a separate page for admin registration
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@ModelAttribute("loginAdmin") UsersDTO userDTO, Model model) {
        if (userService.authenticate(userDTO.getUsername(), userDTO.getPassword())) {
            String role = userService.getUserRole(userDTO.getUsername());
            if ("ADMIN".equals(role)) {
                return "redirect:/admin/index"; // Assuming your admin dashboard is at /admin/index
            } else {
                return "redirect:/index"; // Redirect to home page for regular users
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "Users/pages/login";
        }
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute("adminUser") UsersDTO adminDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Admin/pages/loginAdmin";
        }
        // Assuming you have a method in UserService to save admin users specifically
        userService.saveAdmin(adminDTO);
        return "redirect:/admin/login"; // Redirect to admin login page after successful registration
    }
}
