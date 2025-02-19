package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.entity.Category;
import org.example.delivery_app.repo.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;



    @GetMapping
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void  addCategory(@RequestBody Category category) {
        Category category1=Category.builder()
                .name(category.getName()).build();
        categoryRepository.save(category1);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

}
