package com.example.blog_management.dtos.requests.comments;

import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReqComment {
    private Integer id;
    @Lob
    private String content;
    private ReqUserId reqUserId;
    private ReqBlogId reqBlogId;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
