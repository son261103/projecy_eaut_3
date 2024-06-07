package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.CategoriesDTO;
import com.example.admingiadien.Entity.Categories;
import com.example.admingiadien.Mapper.CategoriesMapper;
import com.example.admingiadien.Repository.CategoriesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;

    // hiển thị danh mục
    public List<CategoriesDTO> showAllCategories(){
        List<Categories> categories = categoriesRepository.findAll();
        return categoriesMapper.toDto(categories);
    }

    // thêm danh mục sản phẩm
    @Transactional
    public CategoriesDTO addCategories(CategoriesDTO categoriesDTO){
        categoriesDTO.setCreatedAt(LocalDateTime.now());
        categoriesDTO.setUpdatedAt(LocalDateTime.now());
        Categories categories = categoriesRepository.save(categoriesMapper.toEntity(categoriesDTO));
        return categoriesMapper.toDto(categories);
    }

    // sửa danh mục sản phẩm
    @Transactional
    public CategoriesDTO getCategoriesById(Long id){
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục sản phẩm id = !" + id));
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);
        return categoriesDTO;
    }

    @Transactional
    public void editCategories(CategoriesDTO categoriesDTO) throws IOException{
        Categories categories = categoriesMapper.toEntity(categoriesDTO);
        categories.setUpdatedAt(LocalDateTime.now());
        categories.setCreatedAt(categoriesDTO.getCreatedAt());
        categoriesRepository.save(categories);
    }

    //xóa danh mục sản phẩm
    @Transactional
    public CategoriesDTO deleteCategories(Long CategoriesId){
        CategoriesDTO categoriesDTO = getCategoriesById(CategoriesId);
        categoriesRepository.deleteById(CategoriesId);
        return categoriesDTO;
    }
}
