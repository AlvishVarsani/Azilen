package com.azilen.training.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ExpensesDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 50, message = "Title must not more than  50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Title can contain only alphabets")
    private String title;

    @Size(max = 500, message = "Details must small than 500 characters")
    private String details;

    @Positive(message = "Amount must be a positive, non-zero value")
    private double amount;

    @PastOrPresent(message = "Expense date cannot be in the future")
    @NotNull(message = "Expense date cannot be null")
    private LocalDate date;

    private int categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
