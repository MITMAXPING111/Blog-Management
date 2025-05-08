package com.example.blog_management.dtos.requests.blogs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqLikeBlog {
    private Integer blogId;
    private Integer userId;
}