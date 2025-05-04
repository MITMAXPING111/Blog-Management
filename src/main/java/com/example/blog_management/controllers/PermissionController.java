package com.example.blog_management.controllers;

import com.example.blog_management.dtos.requests.permissions.ReqPermission;
import com.example.blog_management.dtos.requests.permissions.ReqPermissionId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.services.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqPermission req) {
        RestResponse result = permissionService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = permissionService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqPermissionId permissionId) {
        RestResponse result = permissionService.findById(permissionId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqPermissionId permissionId) {
        RestResponse result = permissionService.deleteById(permissionId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
