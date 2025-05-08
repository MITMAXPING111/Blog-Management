package com.example.blog_management.dtos.requests.blogs;

import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.requests.users.ReqUserId;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReqBlog {
    private Integer id;
    private String title;
    @Lob
    private String content;
    private ReqCategoryId categoryId;
    private ReqUserId userId;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private ReqCategoryId category;
    private ReqUserId user;
}
