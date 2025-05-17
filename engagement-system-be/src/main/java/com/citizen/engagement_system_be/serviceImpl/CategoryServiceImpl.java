package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.CategoryDTO;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.CategoryMapper;
import com.citizen.engagement_system_be.models.Category;
import com.citizen.engagement_system_be.repository.CategoryRepository;
import com.citizen.engagement_system_be.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepo, CategoryMapper categoryMapper) {
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category updatedCategory = categoryMapper.toEntity(categoryDTO);
        updatedCategory.setId(id);
        Category savedCategory = categoryRepo.save(updatedCategory);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepo.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
