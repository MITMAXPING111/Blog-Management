package com.example.blog_management.services.blogs;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface BlogService {
    RestResponse findAll();

    RestResponse findById(ReqBlogId reqBlogId);

    RestResponse createOrUpdate(ReqBlog req);

    RestResponse deleteById(ReqBlogId blogId);

}
