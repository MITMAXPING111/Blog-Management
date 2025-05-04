package com.example.blog_management.dtos.requests.users;

import com.example.blog_management.constants.GenderEnum;
import com.example.blog_management.dtos.requests.roles.ReqRoleId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ReqUser {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private boolean enabled = true; // tài khoản bị khóa/mở
    private String phone;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private Set<ReqRoleId> reqRoleIds = new HashSet<>();
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
