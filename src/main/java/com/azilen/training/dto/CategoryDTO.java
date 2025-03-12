package com.azilen.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryDTO {

    @NotBlank(message = "Category name can't be blank")
    @Pattern(regexp = "^[A-Za-z]{3,20}$",message = "Category name can only contain alphabets")
    private String categoryName;

    @NotBlank(message = "Description can't be blank")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{S}\\s]{1,200}$",
            message = "Description can contain alphabets, numbers, special characters, and spaces (max 200 characters).")
    private String description;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
