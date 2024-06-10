package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.DTO.ProductImagesDTO;
import com.example.admingiadien.DTO.ProductsDTO;
import com.example.admingiadien.Entity.Categories;
import com.example.admingiadien.Entity.ProductImages;
import com.example.admingiadien.Entity.Products;
import com.example.admingiadien.Mapper.CategoriesMapper;
import com.example.admingiadien.Mapper.ProductImagesMapper;
import com.example.admingiadien.Mapper.ProductMapper;
import com.example.admingiadien.Repository.CategoriesRepository;
import com.example.admingiadien.Repository.ProductImagesRepository;
import com.example.admingiadien.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;
    private final FilebaseService filebaseService;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductImagesRepository productImagesRepository;
    private final ProductImagesMapper productImagesMapper;
    //hien thi product
    public List<ProductsDTO> ShowAllProducts() {
        List<Products> productsList = productRepository.findAll();
        List<Categories> categories = categoriesRepository.findAll();
        return productMapper.toDto(productsList);
    }

    // Thêm sản phẩm mới
    public ProductsDTO addProduct(ProductsDTO productDTO, MultipartFile file, Long categoryId) throws IOException {
        String imageUrl = filebaseService.uploadFile(file);
        productDTO.setCreatedAt(LocalDateTime.now());
        productDTO.setUpdatedAt(LocalDateTime.now());
        productDTO.setImage(imageUrl); // Set image URL

        // Gán ID của danh mục cho sản phẩm
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));
        productDTO.setCategoryId(categoryId);

        Products product = productMapper.toEntity(productDTO);
        product.setCategory(category); // Liên kết sản phẩm với danh mục
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


    public List<ProductsDTO> getProductsByCategory(Categories category) {
        List<Products> products = productRepository.findByCategory(category);
        return productMapper.toDto(products);
    }

    public List<ProductsDTO> getProductsByCategoryId(Long categoryId) {
        List<Products> products = productRepository.findByCategoryIdUsingNativeQuery(categoryId);
        return productMapper.toDto(products);
    }

    public ProductsDTO getProductWithImagesById(Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        List<ProductImages> images = productImagesRepository.findByProductsId(productId);
        ProductsDTO productDTO = productMapper.toDto(product);
        List<ProductImagesDTO> imagesDTO = productImagesMapper.toDto(images);
        productDTO.setImages(imagesDTO);
        return productDTO;
    }




}
