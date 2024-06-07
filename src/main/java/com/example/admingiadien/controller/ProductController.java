package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Service.CategoriesService;
import com.example.admingiadien.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriesService categoriesService;

    // Hiển thị sản phẩm
    @GetMapping("/admin/products")
    public String getProductsByCategoryId(@RequestParam("categoryId") Long categoryId, Model model) {
        List<ProductsDTO> products = productService.ShowAllProducts();
        CategoriesDTO category = categoriesService.getCategoriesById(categoryId);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        return "Admin/pages/products/products";
    }

    // Thêm sản phẩm
    @GetMapping("/admin/addproduct")
    public String showAddProductForm(@RequestParam("categoryId") Long categoryId,@RequestParam("categoryId") Long id ,Model model) {
        ProductsDTO product = new ProductsDTO();
        product.setCategoryId(categoryId);
        CategoriesDTO category = categoriesService.getCategoriesById(id);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("product", product);
        return "Admin/pages/products/addproduct";
    }

    @PostMapping("/admin/addproduct")
    public String addProduct(@RequestParam("categoryId") Long categoryId, @ModelAttribute ProductsDTO product, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            productService.addProduct(product, file);
            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding product.");
        }
        return "redirect:/products?categoryId=" + categoryId;
    }

    // Sửa sản phẩm
    @GetMapping("/admin/products/{productId}/edit")
    public String showEditProductForm(@PathVariable Long productId, Model model) {
        ProductsDTO productDTO = productService.getProductById(productId);
        model.addAttribute("productDTO", productDTO);
        List<CategoriesDTO> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "Admin/pages/products/editproduct";
    }

    @PostMapping("/admin/products/{productId}/edit")
    public String editProduct(@PathVariable Long productId, @RequestParam("categoryId") Long categoryId, @ModelAttribute("productDTO") ProductsDTO productDTO, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            productDTO.setUpdatedAt(LocalDateTime.now());
            productService.updateProduct(productId, productDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product.");
        }
        return "redirect:/admin/products?categoryId=" + categoryId;
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/admin/products/{productId}/detail")
    public String showProductDetail(@PathVariable Long productId, Model model) {
        ProductsDTO productsDTO = productService.getProductById(productId);
        model.addAttribute("productDTO", productsDTO);
        return "Admin/pages/products/productdetail";
    }
}
