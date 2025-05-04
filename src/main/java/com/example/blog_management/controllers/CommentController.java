package com.example.blog_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog_management.dtos.requests.comments.ReqComment;
import com.example.blog_management.dtos.requests.comments.ReqCommentId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.services.comment.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqComment req) {
        RestResponse result = commentService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = commentService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqCommentId commentId) {
        RestResponse result = commentService.findById(commentId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqCommentId commentId) {
        RestResponse result = commentService.deleteById(commentId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
