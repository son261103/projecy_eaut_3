package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages , Long> {
    List<ProductImages> findByProductsId(Long productId);
    Optional<ProductImages> findByIdAndProductsId(Long imageId, Long productId);

}
