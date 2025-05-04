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
    private Integer id; // Sử dụng Integer
    private String content;
    private String user_id; // Nhận chuỗi từ JSON
    private String blog_id; // Nhận chuỗi từ JSON
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private ReqUserId reqUserId;
    private ReqBlogId reqBlogId;

}
