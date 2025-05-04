package com.example.blog_management.dtos.responses.permissions;

import com.example.blog_management.constants.PermissionEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResPermission {
    private Integer id;
    private PermissionEnum name;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
