package com.example.admingiadien.Repository;

import com.example.admingiadien.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories , Long> {

}
