package com.example.blog_management.dtos.responses.comments;

import java.time.LocalDateTime;

import com.example.blog_management.dtos.responses.blogs.ResBlogId;
import com.example.blog_management.dtos.responses.users.ResUserId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class ResComment {
    private Integer id; // Sử dụng Integer thay vì Long
    private String content;
    private Integer resUserId; // Sử dụng Integer thay vì Long
    private Integer resBlogId; // Sử dụng Integer thay vì Long
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    public ResComment() {
    }

}
