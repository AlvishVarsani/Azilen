package com.azilen.training.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class FilterDTO {

    @Size(min = 3, message = "Search title must have at least 3 characters")
    private String searchTitle;

    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    @PastOrPresent(message = "End date cannot be in the future")
    private LocalDate endDate;

    private Long categoryId;

    @PositiveOrZero(message = "Minimum amount cannot be negative")
    private Double minAmount;

    @PositiveOrZero(message = "Maximum amount cannot be negative")
    private Double maxAmount;

    private boolean filtered;

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }
}
