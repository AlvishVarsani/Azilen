package com.azilen.training.service;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.entity.Category;
import com.azilen.training.entity.User;
import com.azilen.training.repository.CategoryRepository;
import com.azilen.training.repository.UserDetailsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository, UserDetailsRepository userDetailsRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Convert DTO to Category entity
    private Category convertDtoToCategory(CategoryDTO categoryDto, User user) {
        if (categoryDto == null || user == null || user.getUserDetails() == null) {
            throw new IllegalArgumentException("Invalid category data or user details");
        }
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName() != null ? categoryDto.getCategoryName() : "");
        category.setDescription(categoryDto.getDescription() != null ? categoryDto.getDescription() : "");
        category.setUserDetails(user.getUserDetails());
        return category;
    }

    // Convert Category entity to DTO
    private CategoryDTO convertCategoryToDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public Boolean saveCategory(CategoryDTO categoryDto, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getUserDetails() == null) {
            throw new RuntimeException("User not logged in or user details missing");
        }

        Category category = convertDtoToCategory(categoryDto, user);
        if (categoryRepository.existsByCategoryNameAndUserDetailsId(category.getCategoryName(), user.getUserDetails().getId())) {
            throw new RuntimeException("Category already exists");
        }

        categoryRepository.save(category);
        return true;
    }

    public Page<CategoryDTO> paginationOfAllCategories(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findByUserDetailsId(user.getUserDetails().getId(), pageable);
        return categoryPage.map(this::convertCategoryToDTO);
    }

    public List<CategoryDTO> getAllCategoriesList(User user) {
        List<Category> categoryList = categoryRepository.findByUserDetailsId(user.getUserDetails().getId());
        return categoryList.stream().map(this::convertCategoryToDTO).toList();
    }


}