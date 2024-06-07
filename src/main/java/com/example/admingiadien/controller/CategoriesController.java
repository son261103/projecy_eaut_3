package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    // hiển thị danh mục sản phẩm
    @GetMapping("/admin/categories")
    public String showFormCategories(Model model){
        List<CategoriesDTO> categoriesDTOList = categoriesService.showAllCategories();
        model.addAttribute("categories", categoriesDTOList);
        return "Admin/pages/categories/Categories";
    }

    //thêm danh mục sản phẩm
    @GetMapping("/admin/addcategories")
    public String showFormAddCategories(Model model){
        model.addAttribute("category" , new CategoriesDTO());
        return "Admin/pages/categories/addCategories";
    }

    @PostMapping("/admin/addcategories")
    public String addCategories(@ModelAttribute("category") CategoriesDTO categoriesDTO, RedirectAttributes redirectAttributes){
        try {
            categoriesService.addCategories(categoriesDTO);
            redirectAttributes.addFlashAttribute("successMessage" , "Thêm nhân viên thành công!");
            return "redirect:/admin/categories";
        }catch (RuntimeException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage" ,"Failed to add category: " + e.getMessage());
            return  "redirect:/admin/addcategories";
        }
    }

    // sửa danh mục sản phẩm
    @GetMapping("/admin/editcategories/{id}")
    public String showEditCategories(@PathVariable("id") Long id , Model model){
        CategoriesDTO categoriesDTO = categoriesService.getCategoriesById(id);
        model.addAttribute("category", categoriesDTO);
        return "Admin/pages/categories/editcategories";
    }

    @PostMapping("/admin/editcategories/{id}")
    public String editCategories(@PathVariable("id") Long id,
                                 @ModelAttribute("CategoriesDTO") CategoriesDTO categoriesDTO,
                                 RedirectAttributes redirectAttributes) throws IOException {
        // Retrieve existing category from the database
        CategoriesDTO existingCategory = categoriesService.getCategoriesById(id);
        if (existingCategory != null) {
            existingCategory.setName(categoriesDTO.getName());
            existingCategory.setDescription(categoriesDTO.getDescription());
            existingCategory.setUpdatedAt(categoriesDTO.getUpdatedAt());
            // Save the updated category

            categoriesService.editCategories(existingCategory);
        }
        return "redirect:/admin/categories";
    }

    // xóa danh mục sản phẩm
    @GetMapping("/deletecategories/{categoriesId}")
    public String deleteCategories(@PathVariable Long categoriesId){
        categoriesService.deleteCategories(categoriesId);
        return "redirect:/admin/categories";
    }


}
