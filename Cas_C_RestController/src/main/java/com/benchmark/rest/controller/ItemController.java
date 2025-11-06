package com.benchmark.rest.controller;

import com.benchmark.rest.model.Item;
import com.benchmark.rest.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepo;

    @GetMapping
    public Page<Item> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "50") int size) {
        return itemRepo.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @GetMapping(params = "categoryId")
    public Page<Item> byCategory(@RequestParam Long categoryId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size) {
        return itemRepo.findByCategoryId(categoryId, PageRequest.of(page, size));
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return itemRepo.save(item);
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @RequestBody Item item) {
        Item existing = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setName(item.getName());
        existing.setPrice(item.getPrice());
        existing.setStock(item.getStock());
        existing.setSku(item.getSku());
        existing.setCategory(item.getCategory());
        return itemRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemRepo.deleteById(id);
    }
}
