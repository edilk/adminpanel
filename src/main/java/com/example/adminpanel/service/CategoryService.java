package com.example.adminpanel.service;

import com.example.adminpanel.dto.CategoryDTO;
import com.example.adminpanel.entity.Category;
import com.example.adminpanel.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByTitle(category.getTitle());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category '" + category.getTitle() + "' already exists.");
        }

        return categoryRepository.save(category);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id '" + id + "' not found"));

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setParentId(categoryDTO.getParentId());

        return categoryDTO;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        existingCategory.setTitle(category.getTitle());
        existingCategory.setParentId(category.getParentId());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        List<Category> childCategories = categoryRepository.findByParentId(id);
        for (Category category : childCategories) {
            deleteCategory(category.getId());
        }

        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setParentId(category.getParentId());

        return categoryDTO;
    }
}
