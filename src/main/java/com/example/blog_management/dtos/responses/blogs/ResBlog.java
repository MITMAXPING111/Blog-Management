package com.example.blog_management.dtos.responses.blogs;

import java.time.LocalDateTime;

import com.example.blog_management.dtos.responses.categories.ResCategory;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResBlog {
    private Integer id;
    private String title;
    private String content;
    private ResCategory category;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    public ResBlog() {

    }
}
