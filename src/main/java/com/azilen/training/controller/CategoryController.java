package com.azilen.training.controller;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.entity.User;
import com.azilen.training.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }
        int pageSize = 10;
        Page<CategoryDTO> categoryPage = categoryService.paginationOfAllCategories(user, page, pageSize);

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("hasPrevious", categoryPage.hasPrevious());
        model.addAttribute("hasNext", categoryPage.hasNext());

        return "category";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        model.addAttribute("categoryDto", new CategoryDTO());
        return "category-form";
    }

    @PostMapping("/save-category")
    public String submitCategory(@Valid @ModelAttribute("categoryDto") CategoryDTO categoryDto, BindingResult bindingResult, HttpSession session, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getFieldError());
                return "category-form";
            }

            User user = (User) session.getAttribute("user");
            if (user == null || user.getUserDetails() == null) {
                return "redirect:/login";
            }

            Boolean isSaved = categoryService.saveCategory(categoryDto, session);
            if (isSaved) {
                model.addAttribute("successMsg", "Category created successfully!");
                return "category-form";
            }
            return "category-form";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "category-form";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred");
            return "category-form";
        }
    }
}