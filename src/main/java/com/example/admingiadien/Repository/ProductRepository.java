package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.Categories;
import com.example.admingiadien.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    // Sử dụng JPQL để truy vấn sản phẩm theo danh mục
    @Query("SELECT p FROM Products p WHERE p.category = :category")
    List<Products> findByCategory(Categories category);

    // Sử dụng SQL thuần túy để truy vấn sản phẩm theo danh mục
    @Query(value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE c.id = :categoryId", nativeQuery = true)
    List<Products> findByCategoryIdUsingNativeQuery(Long categoryId);
}

