package com.example.blog_management.services.role;

import com.example.blog_management.dtos.requests.roles.ReqRole;
import com.example.blog_management.dtos.requests.roles.ReqRoleId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface RoleService {
    RestResponse findAll();
    RestResponse findById(ReqRoleId reqRoleId);
    RestResponse createOrUpdate(ReqRole req);
    RestResponse deleteById(ReqRoleId reqRoleId);
}
