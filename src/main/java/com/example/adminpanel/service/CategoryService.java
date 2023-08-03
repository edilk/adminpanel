package com.example.adminpanel.service;

import com.example.adminpanel.dto.CategoryDTO;
import com.example.adminpanel.entity.Category;
import com.example.adminpanel.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {

        return categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
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
