package com.example.blog_management.dtos.requests.permissions;

import com.example.blog_management.constants.PermissionEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReqPermission {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PermissionEnum name;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
