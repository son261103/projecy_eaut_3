package com.example.admingiadien.controller;

import com.example.admingiadien.Entity.Products;
import com.example.admingiadien.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    // Xử lý yêu cầu Ajax để thêm sản phẩm vào giỏ hàng
    @PostMapping("/users/add-to-cart")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody Products product) {
        // Thực hiện logic để thêm sản phẩm vào giỏ hàng
        cartService.addToCart(product);
        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng!");
    }


}
