package com.azilen.training.controller;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.dto.ExpensesDTO;
import com.azilen.training.dto.LoginDTO;
import com.azilen.training.entity.User;
import com.azilen.training.service.CategoryService;
import com.azilen.training.service.ExpenseService;
import com.azilen.training.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    private final UserService userService;
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public LoginController(UserService userService, ExpenseService expenseService, CategoryService categoryService) {
        this.userService = userService;
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/save-login")
    public String login(@ModelAttribute LoginDTO loginDto, Model model , HttpSession session){
            Boolean isLogin = userService.loginUser(loginDto);

            if(!isLogin) {
                model.addAttribute("error", "Login credentials fails");
                return "login";
            }

            User user=userService.findByUserName(loginDto.getUsername());
            session.setAttribute("user",user);
        int page=0;
        int pageSize=10;
        //this is use for showing the category name
        List<CategoryDTO> categoryList=categoryService.getAllCategoriesList(user);
        Map<Integer, String> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(CategoryDTO::getId, CategoryDTO::getCategoryName));

        Page<ExpensesDTO> expensesPage = expenseService.getPaginatedExpenses(user, page, pageSize);
        model.addAttribute("successMsg", "true");
        model.addAttribute("user", user);
        model.addAttribute("expenses", expensesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", expensesPage.getTotalPages());
        model.addAttribute("categoryMap", categoryMap);
        return "expenses";
    }
}
