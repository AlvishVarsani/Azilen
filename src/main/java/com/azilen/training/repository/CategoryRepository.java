package com.azilen.training.repository;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.entity.Category;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Page<Category> findByUserDetailsId(int userId, Pageable pageable);
    List<Category> findByUserDetailsId(int userId);
    Category findById(int id);
    Boolean existsByCategoryNameAndUserDetailsId(String categoryName,int userId);
}
