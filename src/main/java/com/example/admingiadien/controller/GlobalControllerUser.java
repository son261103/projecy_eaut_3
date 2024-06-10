package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerUser {

    private final CategoriesService categoriesService;

    @Autowired
    public GlobalControllerUser(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @ModelAttribute
    public void categories(Model model) {
        List<CategoriesDTO> categories = categoriesService.showAllCategories();
        model.addAttribute("categories", categories);
    }
}
