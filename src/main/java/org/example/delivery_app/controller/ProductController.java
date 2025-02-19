package org.example.delivery_app.controller;

import lombok.RequiredArgsConstructor;
import org.example.delivery_app.dto.ProductSaveDTO;
import org.example.delivery_app.entity.Product;
import org.example.delivery_app.repo.AttachmentRepository;
import org.example.delivery_app.repo.CategoryRepository;
import org.example.delivery_app.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(product);
    }



    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        // Fetch products based on categoryId from database
        return productRepository.findByCategoryId(categoryId);
    }


    @GetMapping
    public List<Product> getProductsByCategory(@RequestParam(required = false) Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @GetMapping("{id}")
    public Product getOneProduct(@PathVariable Integer id) {
        return productRepository.findById(id).orElseThrow();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void saveProduct(@RequestBody ProductSaveDTO productSaveDTO) {
        Product product =   Product.builder()
                .name(productSaveDTO.getName())
                .price(productSaveDTO.getPrice())
                .category(categoryRepository.findById(productSaveDTO.getCategoryId()).orElseThrow())
                .attachment(attachmentRepository.findById(productSaveDTO.getAttachmentId()).orElseThrow())
                .build();
        productRepository.save(product);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductSaveDTO productSaveDTO) {
        Product product =   Product.builder()
                .id(id)
                .name(productSaveDTO.getName())
                .price(productSaveDTO.getPrice())
                .category(categoryRepository.findById(productSaveDTO.getCategoryId()).orElseThrow())
                .attachment(attachmentRepository.findById(productSaveDTO.getAttachmentId()).orElseThrow())
                .build();
        productRepository.save(product);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);

    }
}
