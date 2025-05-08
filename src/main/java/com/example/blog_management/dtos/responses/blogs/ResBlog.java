package com.example.blog_management.dtos.responses.blogs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.blog_management.dtos.responses.categories.ResCategory;
import com.example.blog_management.dtos.responses.comments.ResComment;
import com.example.blog_management.dtos.responses.users.ResUser;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResBlog {
    private Integer id;
    private String title;
    @Lob
    private String content;
    private int liked;

    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    private ResCategory resCategory;
    private ResUser resUser;
    private List<ResComment> resComments = new ArrayList<>();
}
