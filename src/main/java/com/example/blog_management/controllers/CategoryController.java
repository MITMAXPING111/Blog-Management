package com.example.blog_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog_management.dtos.requests.categories.ReqCategory;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.services.categories.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqCategory req) {
        RestResponse result = categoryService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = categoryService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqCategoryId categoryId) {
        RestResponse result = categoryService.findById(categoryId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqCategoryId categoryId) {
        RestResponse result = categoryService.deleteById(categoryId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
