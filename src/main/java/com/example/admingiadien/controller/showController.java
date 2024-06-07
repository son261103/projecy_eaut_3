package com.example.admingiadien.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class showController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("admin/index")
    public String adminIndex() {
        return "Admin/index";
    }


}
