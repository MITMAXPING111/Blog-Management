package com.example.blog_management.services.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.requests.comments.ReqComment;
import com.example.blog_management.dtos.requests.comments.ReqCommentId;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.dtos.responses.comments.ResComment;
import com.example.blog_management.entities.Blog;
import com.example.blog_management.entities.Comment;
import com.example.blog_management.entities.User;
import com.example.blog_management.repositories.BlogRepo;
import com.example.blog_management.repositories.CommentRepo;
import com.example.blog_management.repositories.UserRepo;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo; // Inject UserRepository

    @Autowired
    private BlogRepo blogRepo; // Inject BlogRepository

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Comment> comments = commentRepo.findAll();
            List<ResComment> result = new ArrayList<>();

            for (Comment p : comments) {
                ResComment resComment = modelMapper.map(p, ResComment.class);

                result.add(resComment);
            }

            restResponse.setMessage("Find all Comment success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all Comment: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqCommentId commentId) {
        RestResponse restResponse = new RestResponse();

        try {
            Comment comment = commentRepo.findById(commentId.getId()).orElse(null);
            ResComment result = modelMapper.map(comment, ResComment.class);

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
            if (req.getId() != null && commentRepo.existsById(req.getId())) {
                Comment comment = commentRepo.findById(req.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                req.setCreateAt(comment.getCreateAt());
                req.setCreateBy(comment.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");

                // Map User to ReqUserId
                ReqUserId reqUserId = new ReqUserId();
                reqUserId.setId(comment.getUser().getId());
                req.setReqUserId(reqUserId);

                // Map Blog to ReqBlogId
                ReqBlogId reqBlogId = new ReqBlogId();
                reqBlogId.setId(comment.getBlog().getId());
                req.setReqBlogId(reqBlogId);

                update = true;
            } else {
                // Handle create new comment
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");

                // Map user_id to ReqUserId
                ReqUserId reqUserId = new ReqUserId();
                try {
                    reqUserId.setId(Integer.valueOf(req.getUser_id()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid user_id format: " + req.getUser_id());
                }
                req.setReqUserId(reqUserId);

                // Map blog_id to ReqBlogId
                ReqBlogId reqBlogId = new ReqBlogId();
                try {
                    reqBlogId.setId(Integer.valueOf(req.getBlog_id()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid blog_id format: " + req.getBlog_id());
                }
                req.setReqBlogId(reqBlogId);
            }

            // Map ReqComment to Comment
            Comment comment = modelMapper.map(req, Comment.class);

            // Kiểm tra và gán User
            User user = userRepo.findById(req.getReqUserId().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + req.getReqUserId().getId()));
            comment.setUser(user);

            // Kiểm tra và gán Blog
            Blog blog = blogRepo.findById(req.getReqBlogId().getId())
                    .orElseThrow(() -> new RuntimeException("Blog not found with id: " + req.getReqBlogId().getId()));
            comment.setBlog(blog);

            // Lưu Comment
            commentRepo.save(comment);

            // Map Comment to ResComment
            ResComment result = modelMapper.map(comment, ResComment.class);

            restResponse.setMessage(update ? "Update comment success" : "Create comment success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update comments: " + e.getMessage())
                    : ("Failed to create comments: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqCommentId commentId) {
        RestResponse restResponse = new RestResponse();

        try {
            commentRepo.deleteById(commentId.getId());

            restResponse.setMessage("Delete comment success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete comments: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
