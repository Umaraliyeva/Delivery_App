package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.entity.Category;
import org.example.delivery_app.repo.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        // 1️⃣ Mavjud kategoriya nomini tekshiramiz
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bunday nomli kategoriya allaqachon mavjud! Iltimos, boshqa nom kiriting.");
        }

        // 2️⃣ Agar kategoriya mavjud bo‘lmasa, uni yaratamiz
        Category newCategory = Category.builder()
                .name(category.getName())
                .build();
        categoryRepository.save(newCategory);

        return ResponseEntity.ok("Kategoriya muvaffaqiyatli qo'shildi!");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        // 1️⃣ Kategoriya mavjudligini tekshiramiz
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoriya topilmadi!"));

        // 2️⃣ Yangi nom allaqachon boshqa kategoriya tomonidan band emasligini tekshiramiz
        if (categoryRepository.findByName(category.getName()).isPresent() &&
                !existingCategory.getName().equals(category.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bunday nomli kategoriya allaqachon mavjud! Iltimos, boshqa nom tanlang.");
        }

        // 3️⃣ Kategoriya nomini yangilaymiz
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);

        return ResponseEntity.ok("Kategoriya muvaffaqiyatli yangilandi!");
    }

}
