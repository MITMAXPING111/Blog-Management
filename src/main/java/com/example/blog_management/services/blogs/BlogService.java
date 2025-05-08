package com.example.blog_management.services.blogs;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface BlogService {
    RestResponse findAll();

    RestResponse findById(ReqBlogId reqBlogId);

    RestResponse createOrUpdate(ReqBlog req);

    RestResponse deleteById(ReqBlogId reqBlogId);

    RestResponse likeBlog(Integer blogId, Integer userId);

    RestResponse dislikeBlog(Integer blogId, Integer userId);

    RestResponse searchTitle(String searchTitle);

    RestResponse filterUser(ReqUserId reqUserId);

    RestResponse filterCategory(ReqCategoryId reqCategoryId);
}
