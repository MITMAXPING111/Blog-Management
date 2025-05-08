package com.example.blog_management.dtos.responses.comments;

import java.time.LocalDateTime;

import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.dtos.responses.blogs.ResBlogId;
import com.example.blog_management.dtos.responses.users.ResUser;
import com.example.blog_management.dtos.responses.users.ResUserId;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResComment {
    private Integer id;
    @Lob
    private String content;

    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    private ResUser resUser;

    private ResBlog resBlog;
}
