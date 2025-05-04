package com.example.blog_management.dtos.responses.roles;

import com.example.blog_management.constants.PermissionEnum;
import com.example.blog_management.constants.RoleEnum;
import com.example.blog_management.dtos.responses.permissions.ResPermission;
import com.example.blog_management.entities.Permission;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ResRole {
    private Integer id;
    private RoleEnum name;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private Set<ResPermission> resPermissions = new HashSet<>();
}
