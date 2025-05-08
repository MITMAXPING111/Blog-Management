package com.example.blog_management.dtos.responses.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.blog_management.constants.GenderEnum;
import com.example.blog_management.dtos.responses.roles.ResRole;
import com.example.blog_management.dtos.responses.roles.ResRoleId;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class ResUser {
    private Integer id;
    private String email;
    private String name;
    private boolean enabled = true; // tài khoản bị khóa/mở
    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private Set<ResRole> resRoles = new HashSet<>();
}
