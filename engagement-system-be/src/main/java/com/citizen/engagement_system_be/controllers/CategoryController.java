package com.citizen.engagement_system_be.controllers;

import com.citizen.engagement_system_be.dtos.CategoryDTO;
import com.citizen.engagement_system_be.models.Category;
import com.citizen.engagement_system_be.repository.CategoryRepository;
import com.citizen.engagement_system_be.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v3/categories")
@Tag(name = "Category management", description = "APIs for managing complaint categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService,CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Operation(summary = "Create new category")
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id));
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

//    @Operation(summary = "Search categories")
//    @GetMapping("/search")
//    public ResponseEntity<SearchResultDTO<CategoryDTO>> searchCategories(CategorySearchDTO searchDTO) {
//        return ResponseEntity.ok(categoryService.searchCategories(searchDTO));
//    }
}
