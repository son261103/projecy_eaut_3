package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.ProductImagesDTO;
import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Entity.ProductImages;
import com.example.admingiadien.Entity.Products;
import com.example.admingiadien.Repository.ProductImagesRepository;
import com.example.admingiadien.Mapper.ProductImagesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class ProductImagesService {

    private final FilebaseService filebaseService;
    private final ProductImagesRepository productImagesRepository;
    private final ProductImagesMapper productImagesMapper;
    private final ProductService productService;

    public String saveProductImage(ProductImagesDTO productImagesDTO, Long productId, MultipartFile file) throws IOException {
        String imageUrl = filebaseService.uploadFile(file);
        Products product = productService.getProductByIds(productId);
        ProductImages productImage = productImagesMapper.toEntity(productImagesDTO);
        productImage.setProducts(product);
        productImage.setImageUrl(imageUrl);
        productImage.setCreatedAt(LocalDateTime.now());
        productImage.setUpdatedAt(LocalDateTime.now());
        productImagesRepository.save(productImage);
        return imageUrl; // Return the URL of the saved image
    }

    public List<ProductImagesDTO> getProductImages(Long productId) {
        List<ProductImages> productImages = productImagesRepository.findByProductsId(productId);
        return productImagesMapper.toDto(productImages);
    }

    public void updateProductImage(ProductImagesDTO productImagesDTO, Long productId, Long imageId, MultipartFile file) throws IOException {
        if (productId == null || imageId == null || file == null) {
            throw new IllegalArgumentException("Product ID, Image ID, and File must not be null.");
        }

        Optional<ProductImages> optionalProductImage = productImagesRepository.findByIdAndProductsId(imageId, productId);
        ProductImages productImage = optionalProductImage.orElseThrow(() -> new RuntimeException("Product image not found with id: " + imageId + " for product id: " + productId));

        String imageUrl = filebaseService.uploadFile(file);
        productImage.setImageUrl(imageUrl);
        productImage.setUpdatedAt(LocalDateTime.now());
        productImagesRepository.save(productImage);
    }

    public void deleteProductImage(Long productId, Long imageId) {
        if (productId == null || imageId == null) {
            throw new IllegalArgumentException("Product ID and Image ID must not be null.");
        }

        Optional<ProductImages> optionalProductImage = productImagesRepository.findByIdAndProductsId(imageId, productId);
        ProductImages productImage = optionalProductImage.orElseThrow(() -> new RuntimeException("Product image not found with id: " + imageId + " for product id: " + productId));
        productImagesRepository.delete(productImage);
    }

}
