package com.example.blog_management.controllers;

import com.example.blog_management.dtos.requests.roles.ReqRole;
import com.example.blog_management.dtos.requests.roles.ReqRoleId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqRole req){
        RestResponse result = roleService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        RestResponse result = roleService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqRoleId roleId){
        RestResponse result = roleService.findById(roleId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqRoleId roleId){
        RestResponse result = roleService.deleteById(roleId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
