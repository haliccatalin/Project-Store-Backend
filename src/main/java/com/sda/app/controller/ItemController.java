package com.sda.app.controller;

import com.sda.app.entity.Category;
import com.sda.app.entity.Item;
import com.sda.app.service.ItemService;
import com.sda.app.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllItems() {
        List<Item> itemsList = itemService.findAll();
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista items generata cu success")
                .data(itemsList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getItemById(@PathVariable("id") Integer id) {
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Item by ID")
                .data(itemService.getById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item) {
        Item it = new Item();
        it.setTitle(item.getTitle());
        it.setDescription(item.getDescription());
        it.setCategory(Category.valueOf(item.getCategory().toString()));
        it.setPrice(item.getPrice());
        it.setImageUrl(item.getImageUrl());

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Item creat cu success")
                .data(itemService.createItem(it))
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody Item item, @PathVariable("id") Integer id) {
        System.out.println(id);
        Item it = new Item();
        it.setId(item.getId());
        it.setTitle(item.getTitle());
        it.setDescription(item.getDescription());
        it.setCategory(Category.valueOf(item.getCategory().toString()));
        it.setPrice(item.getPrice());
        it.setImageUrl(item.getImageUrl());

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Item actulizat cu success")
                .data(itemService.updateItem(item))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("id") Integer id) {
        itemService.deleteItem(id);
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Item sters cu success")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

}
