package com.example.blog_management.dtos.requests.roles;

import com.example.blog_management.constants.RoleEnum;
import com.example.blog_management.dtos.requests.permissions.ReqPermissionId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ReqRole {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    private Set<ReqPermissionId> reqPermissionIds = new HashSet<>();
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
