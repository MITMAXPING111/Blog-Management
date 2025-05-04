package com.example.blog_management.services.permission;

import com.example.blog_management.dtos.requests.permissions.ReqPermission;
import com.example.blog_management.dtos.requests.permissions.ReqPermissionId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface PermissionService {
    RestResponse findAll();
    RestResponse findById(ReqPermissionId permissionId);
    RestResponse createOrUpdate(ReqPermission req);
    RestResponse deleteById(ReqPermissionId permissionId);
}
