package com.example.blog_management.services.blogs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.entities.Blog;
import com.example.blog_management.repositories.BlogRepo;

@Service
public class BlogServiceImlp implements BlogService {

    @Autowired
    BlogRepo blogRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Blog> blogs = blogRepo.findAll();
            List<ResBlog> result = new ArrayList<>();

            for (Blog p : blogs) {
                ResBlog resBlog = modelMapper.map(p, ResBlog.class);

                result.add(resBlog);
            }

            restResponse.setMessage("Find all Blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all Blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqBlogId blogId) {
        RestResponse restResponse = new RestResponse();

        try {
            Blog blog = blogRepo.findById(blogId.getId()).orElse(null);
            ResBlog result = modelMapper.map(blog, ResBlog.class);

            restResponse.setMessage("Find blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqBlog req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if (req.getId() != null && blogRepo.existsById(req.getId())) {
                Blog blog = blogRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(blog.getCreateAt());
                req.setCreateBy(blog.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());

                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else {
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            Blog blog = modelMapper.map(req, Blog.class);
            blogRepo.save(blog);
            ResBlog result = modelMapper.map(blog, ResBlog.class);

            restResponse.setMessage(update ? "Update blog success" : "Create blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update blogs: " + e.getMessage())
                    : ("Failed to create blogs: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqBlogId blogId) {
        RestResponse restResponse = new RestResponse();

        try {
            blogRepo.deleteById(blogId.getId());

            restResponse.setMessage("Delete blog success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete blogs: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
