package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.UsersDTO;
import com.example.admingiadien.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/defaul")
    public String defaultAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Admin"));
            if (isAdmin) {
                return "redirect:/admin/index";
            }
        }
        return "redirect:/index";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute("loginUser") UsersDTO userDTO, Model model) {
        if (userService.authenticate(userDTO.getUsername(), userDTO.getPassword())) {
            if (userService.isAdmin(userDTO.getUsername())) {
                return "redirect:/admin/index";
            } else {
                return "redirect:/index";
            }
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "Users/pages/login";
        }
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("registerUser") UsersDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Users/pages/login";
        }
        userService.saveUser(userDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("registerUser", new UsersDTO());
        return "Admin/pages/loginAdmin";
    }

    @PostMapping("/admin/registerAdmin")
    public String registerAdmin(@ModelAttribute("registerUser") UsersDTO userDTO) {
        userService.saveAdmin(userDTO);
        return "redirect:/admin/index";
    }

}
