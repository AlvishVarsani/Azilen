package com.azilen.training.service;

import com.azilen.training.dto.CategoryDTO;
import com.azilen.training.dto.ExpensesDTO;
import com.azilen.training.dto.FilterDTO;
import com.azilen.training.entity.Category;
import com.azilen.training.entity.Expenses;
import com.azilen.training.entity.User;
import com.azilen.training.repository.CategoryRepository;
import com.azilen.training.repository.ExpensesRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpensesRepository expensesRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public ExpenseService(ExpensesRepository expensesRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.expensesRepository = expensesRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    private Expenses convertDtoToExpenses(ExpensesDTO expensesDto, User user, Category category) {
        Expenses expenses=new Expenses();
        if(expensesDto!=null){
            expenses.setTitle(expensesDto.getTitle());
            expenses.setDetails(expensesDto.getDetails());
            expenses.setAmount(expensesDto.getAmount());
            expenses.setDate(expensesDto.getDate());
            expenses.setUserDetails(user.getUserDetails());
            expenses.setCategory(category);
            return expenses;
        }else{
            return null;
        }
    }

    private ExpensesDTO convertExpenseToDto(Expenses expenses){
        ExpensesDTO expensesDTO=new ExpensesDTO();
        expensesDTO.setTitle(expenses.getTitle());
        expensesDTO.setDetails(expenses.getDetails());
        expensesDTO.setAmount(expenses.getAmount());
        expensesDTO.setDate(expenses.getDate());
        expensesDTO.setCategoryId(expenses.getCategory().getId());
        return expensesDTO;
    }

    public Boolean saveExpenses(ExpensesDTO expensesDto, HttpSession session) throws Exception {
        try {

            User user = (User) session.getAttribute("user");
            System.out.println(user);
            if (user == null) {
                throw new RuntimeException("User not logged in");
            }

            Category category = categoryRepository.findById(expensesDto.getCategoryId());

            Expenses expenses = convertDtoToExpenses(expensesDto, user, category);

            if (expenses != null) {
                expensesRepository.save(expenses);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public Page<ExpensesDTO> getPaginatedExpenses(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expenses> expensesPage = expensesRepository.findByUserDetailsId(user.getUserDetails().getId(), pageable);
        return expensesPage.map(this::convertExpenseToDto);
    }


    public Page<ExpensesDTO> getPaginatedExpenses(User user, int page, int size, FilterDTO filter) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expenses> expensesPage = expensesRepository.findFilteredExpenses(
                user.getUserDetails().getId(),
                filter.getMinAmount(),
                filter.getMaxAmount(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getCategoryId() != null ? filter.getCategoryId().intValue() : null,
                filter.getSearchTitle(),
                pageable
        );
        return expensesPage.map(this::convertExpenseToDto);
    }

    public void downloadFilteredReport(User user, FilterDTO filter, HttpServletResponse response) throws IOException {
        try{
            //this is use to get category name in csv file
            List<CategoryDTO> categoryList = categoryService.getAllCategoriesList(user);
            Map<Integer, String> categoryMap = categoryList.stream()
                    .collect(Collectors.toMap(CategoryDTO::getId, CategoryDTO::getCategoryName));

            Page<ExpensesDTO> reportData;
            boolean isFilterNotNull= filter.isFiltered() && (filter.getMinAmount() != null || filter.getMaxAmount() != null ||
                    filter.getStartDate() != null || filter.getEndDate() != null ||
                    filter.getCategoryId() != null ||  filter.getSearchTitle() != null);
            if (isFilterNotNull) {
                reportData = getPaginatedExpenses(user, 0, Integer.MAX_VALUE, filter);
            } else {
                reportData = getPaginatedExpenses(user, 0, Integer.MAX_VALUE);
            }

            // CSV Generation
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=Report_Expenses.csv");

            PrintWriter writer = response.getWriter();
            writer.println("Sr. No.,Title,Details,Amount,Date,Category");

            List<ExpensesDTO> expensesList = reportData.getContent();
            if (reportData.isEmpty()) {
                writer.println("No records found for the selected criteria.");
            } else {
                int index = 1;
                for (ExpensesDTO expense : expensesList) {
                    String categoryName = categoryMap.getOrDefault(expense.getCategoryId(), "Unknown Category");
                    writer.println(index++ + "," +
                            expense.getTitle().replace(",", " ") + "," +
                            expense.getDetails().replace(",", " ") + "," +
                            expense.getAmount() + "," +
                            expense.getDate() + "," +
                            categoryName);
                }
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            throw new IOException(e.getMessage());
        }
        }
}
