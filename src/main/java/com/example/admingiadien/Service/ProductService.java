package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Entity.Categories;
import com.example.admingiadien.Entity.Products;
import com.example.admingiadien.Mapper.CategoriesMapper;
import com.example.admingiadien.Mapper.ProductMapper;
import com.example.admingiadien.Repository.CategoriesRepository;
import com.example.admingiadien.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;
    private final FilebaseService filebaseService;

    //hien thi product
    public List<ProductsDTO> ShowAllProducts() {
        List<Products> productsList = productRepository.findAll();
        List<Categories> categories = categoriesRepository.findAll();
        return productMapper.toDto(productsList);
    }

    // Thêm sản phẩm mới
    public ProductsDTO addProduct(ProductsDTO productDTO, MultipartFile file) throws IOException {
        String imageUrl = filebaseService.uploadFile(file);
        productDTO.setCreatedAt(LocalDateTime.now());
        productDTO.setUpdatedAt(LocalDateTime.now());
        productDTO.setImage(imageUrl); // Set image URL
        Products product = productMapper.toEntity(productDTO);
        productRepository.save(product);
        return productMapper.toDto(product);
    }


    // Lấy sản phẩm theo ID
    public ProductsDTO getProductById(Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        return productMapper.toDto(product);
    }

    // Lấy sản phẩm theo ID
    public Products getProductByIds(Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        return product;
    }

    // Lấy danh sách các danh mục
    public List<CategoriesDTO> getAllCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return categoriesMapper.toDto(categories);
    }


    // Sửa sản phẩm
    public ProductsDTO updateProduct(Long productId, ProductsDTO productDTO, MultipartFile file) throws IOException {
        Products existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        String imageUrl = filebaseService.uploadFile(file);
        LocalDateTime createdAt = existingProduct.getCreatedAt();
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setIsactive(productDTO.getIsactive());
        existingProduct.setCreatedAt(createdAt);
        existingProduct.setUpdatedAt(LocalDateTime.now());
        existingProduct.setImage(imageUrl); // Set image URL

        Products updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

}
