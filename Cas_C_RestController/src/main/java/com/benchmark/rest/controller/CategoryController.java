package com.benchmark.rest.controller;

import com.benchmark.rest.model.Category;
import com.benchmark.rest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public List<Category> list() {
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepo.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        Category existing = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setName(category.getName());
        existing.setCode(category.getCode());
        return categoryRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
    }
}
