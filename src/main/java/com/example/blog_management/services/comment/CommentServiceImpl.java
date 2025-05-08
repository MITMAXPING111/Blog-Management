package com.example.blog_management.services.comment;

import com.example.blog_management.dtos.requests.comments.ReqComment;
import com.example.blog_management.dtos.requests.comments.ReqCommentId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.dtos.responses.comments.ResComment;
import com.example.blog_management.dtos.responses.users.ResUser;
import com.example.blog_management.entities.Blog;
import com.example.blog_management.entities.Comment;
import com.example.blog_management.entities.User;
import com.example.blog_management.repositories.BlogRepo;
import com.example.blog_management.repositories.CategoryRepo;
import com.example.blog_management.repositories.CommentRepo;
import com.example.blog_management.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    BlogRepo blogRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Comment> comments = commentRepo.findAll();
            List<ResComment> result = new ArrayList<>();
            for (Comment comment : comments) {
                ResComment resComment = modelMapper.map(comment, ResComment.class);

                ResUser user = modelMapper.map(comment.getUser(), ResUser.class);
                resComment.setResUser(user);

                ResBlog blog = modelMapper.map(comment.getBlog(), ResBlog.class);
                resComment.setResBlog(blog);

                result.add(resComment);
            }

            restResponse.setMessage("Find comment success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get comment: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqCommentId reqCommentId) {
        RestResponse restResponse = new RestResponse();

        try {
            Comment comment = commentRepo.findById(reqCommentId.getId()).orElse(null);
            ResComment result = modelMapper.map(comment, ResComment.class);

            ResUser user = modelMapper.map(comment.getUser(), ResUser.class);
            result.setResUser(user);

            ResBlog blog = modelMapper.map(comment.getBlog(), ResBlog.class);
            result.setResBlog(blog);

            restResponse.setMessage("Find comment success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get comment: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqComment req) {
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

            Comment comment = modelMapper.map(req, Comment.class);

            // nếu update sẽ không cho update user, blog
            if (!update) {
                User user = modelMapper.map(req.getReqUserId(), User.class);
                comment.setUser(user);

                Blog blog = modelMapper.map(req.getReqBlogId(), Blog.class);
                comment.setBlog(blog);
            }

            commentRepo.save(comment);
            ResComment result = modelMapper.map(comment, ResComment.class);

            User user = userRepo.findById(comment.getUser().getId()).orElse(null);
            ResUser resUser = modelMapper.map(user, ResUser.class);
            result.setResUser(resUser);

            Blog blog = blogRepo.findById(comment.getBlog().getId()).orElse(null);
            ResBlog resBlog = modelMapper.map(blog, ResBlog.class);
            result.setResBlog(resBlog);

            restResponse.setMessage(update ? "Update blog success" : "Create blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update blog: " + e.getMessage())
                    : ("Failed to create blog: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqCommentId reqCommentId) {
        RestResponse restResponse = new RestResponse();

        try {
            blogRepo.deleteById(reqCommentId.getId());

            restResponse.setMessage("Delete comment success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete comment: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
