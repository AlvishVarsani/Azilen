package com.azilen.training.controller;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.dto.ExpensesDTO;
import com.azilen.training.dto.FilterDTO;
import com.azilen.training.entity.User;
import com.azilen.training.service.CategoryService;
import com.azilen.training.service.ExpenseService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ExpensesController {
    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    public ExpensesController(CategoryService categoryService, ExpenseService expenseService) {
        this.categoryService = categoryService;
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public String expensesPage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @ModelAttribute FilterDTO filter,
                               HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "login";
            }

            List<CategoryDTO> categoryList = categoryService.getAllCategoriesList(user);
            Map<Integer, String> categoryMap = categoryList.stream()
                    .collect(Collectors.toMap(CategoryDTO::getId, CategoryDTO::getCategoryName));

            Page<ExpensesDTO> expensesPage = expenseService.getPaginatedExpenses(user, page, size, filter);

            model.addAttribute("user", user);
            model.addAttribute("expenses", expensesPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", expensesPage.getTotalPages());
            model.addAttribute("categoryMap", categoryMap);
            model.addAttribute("filter", filter);
            model.addAttribute("filterDto",new FilterDTO());

            return "expenses";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "expenses";
        }
    }

    @GetMapping("/add-expenses")
    public String addExpenses(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "login";
            }

            List<CategoryDTO> listOfCategory = categoryService.getAllCategoriesList(user);
            model.addAttribute("categories", listOfCategory);
            model.addAttribute("expensesDto",new ExpensesDTO());
            return "expensesform";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "expensesform";
        }
    }

    @PostMapping("/save-expenses")
    public String saveExpenses(@Valid  @ModelAttribute ExpensesDTO expensesDto, BindingResult result, HttpSession session, Model model) {
        if(result.hasErrors()){
            model.addAttribute("errors", result.getFieldError());
            return "expensesform";
        }
        try {
            User user = (User) session.getAttribute("user");
            Boolean isSaved = expenseService.saveExpenses(expensesDto, session);
            if (!isSaved) {
                model.addAttribute("error","Expenses is not saved");
                return "expensesform";
            }
            int page = 0;
            int pageSize = 10;
            Page<ExpensesDTO> expensesPage = expenseService.getPaginatedExpenses(user, page, pageSize);
            //this is use for getting name of category
            List<CategoryDTO> categoryList = categoryService.getAllCategoriesList(user);
            Map<Integer, String> categoryMap = categoryList.stream()
                    .collect(Collectors.toMap(CategoryDTO::getId, CategoryDTO::getCategoryName));
            model.addAttribute("successMsg", "Expenses added successfully!");

            return "expensesform";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "expensesform";
        }
    }

    @GetMapping("/download-report")
    public String downloadReport(@Valid @ModelAttribute FilterDTO filterDTO,
                               BindingResult result,
                               HttpServletResponse response,
                               Model model,
                               HttpSession session) throws IOException {
        if(result.hasErrors()){
            model.addAttribute("error",result.getFieldError());
            return "expenses";
        }

            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("/");
                return "expenses";
            }

            expenseService.downloadFilteredReport(user, filterDTO, response);
            return "expenses";
    }
}
