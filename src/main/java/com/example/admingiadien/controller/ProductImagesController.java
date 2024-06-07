package com.example.admingiadien.controller;

import com.example.admingiadien.DTO.ProductImagesDTO;
import com.example.admingiadien.Service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductImagesController {

    @Autowired
    private ProductImagesService productImagesService;

    // API endpoint để lưu ảnh sản phẩm
    @PostMapping("/admin/products/{productId}/images")
    public ResponseEntity<String> uploadProductImage(@PathVariable String productId, @RequestParam("file") MultipartFile file) {
        try {
            ProductImagesDTO productImageDTO = new ProductImagesDTO();
            String imageUrl = productImagesService.saveProductImage(productImageDTO, Long.parseLong(productId), file); // Lưu ảnh sản phẩm
            return ResponseEntity.ok(imageUrl);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi không thể chuyển đổi String sang Long
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid productId format.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // API endpoint để lấy danh sách ảnh sản phẩm của một sản phẩm
    @GetMapping("/admin/products/{productId}/images")
    @ResponseBody
    public List<ProductImagesDTO> getProductImages(@PathVariable String productId) {
        return productImagesService.getProductImages(Long.parseLong(productId));
    }

    // API endpoint để sửa ảnh sản phẩm
    @PutMapping("/admin/products/{productId}/images/{imageId}")
    public ResponseEntity<String> updateProductImage(@PathVariable Long productId, @PathVariable Long imageId, @RequestParam("file") MultipartFile file) {
        try {
            ProductImagesDTO productImageDTO = new ProductImagesDTO();
            productImagesService.updateProductImage(productImageDTO, productId, imageId, file); // Sửa ảnh sản phẩm
            return ResponseEntity.ok("Image updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image.");
        }
    }

    // API endpoint để xóa ảnh sản phẩm
    @DeleteMapping("/admin/products/{productId}/images/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long productId, @PathVariable Long imageId) {
        try {
            productImagesService.deleteProductImage(productId, imageId); // Xóa ảnh sản phẩm
            return ResponseEntity.ok("Image deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image.");
        }
    }
}
