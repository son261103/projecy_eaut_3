package com.example.admingiadien.Service;
import com.example.admingiadien.Entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class CartService {

    @Autowired
    private TemplateEngine templateEngine;
    // Danh sách các sản phẩm trong giỏ hàng, với key là ID của sản phẩm và value là số lượng sản phẩm
    private final Map<Long, Long> cartItems = new HashMap<>();

    public void addToCart(Products product) {
        Long productId = product.getId();

        if (cartItems.containsKey(productId)) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng lên 1
            cartItems.compute(productId, (k, quantity) -> quantity + 1);
        } else {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào với số lượng là 1
            cartItems.put(productId, 1L);
        }
    }

    public void removeFromCart(Long productId) {
        if (cartItems.containsKey(productId)) {
            // Xóa sản phẩm khỏi giỏ hàng nếu tồn tại
            cartItems.remove(productId);
        }
    }

    public void clearCart() {
        // Xóa toàn bộ sản phẩm khỏi giỏ hàng
        cartItems.clear();
    }

}
