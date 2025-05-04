package com.example.blog_management.dtos.responses.categories;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResCategory {
    private Integer id;
    private String name;
    private String description;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    public ResCategory() {
    }
}
