package com.example.blog_management.dtos.responses.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.blog_management.constants.GenderEnum;
import com.example.blog_management.dtos.responses.roles.ResRoleId;

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
    private boolean enabled;
    private String phone;
    private GenderEnum gender;
    private String createBy;
    private Set<ResRoleId> resRoleIds = new HashSet<>();
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    public ResUser() {
    }
}
