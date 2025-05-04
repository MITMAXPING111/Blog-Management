package com.example.blog_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.services.blogs.BlogService;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {
    @Autowired
    BlogService blogService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqBlog req) {
        RestResponse result = blogService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = blogService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqBlogId blogId) {
        RestResponse result = blogService.findById(blogId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqBlogId blogId) {
        RestResponse result = blogService.deleteById(blogId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
