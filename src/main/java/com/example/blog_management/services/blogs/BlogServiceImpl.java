package com.example.blog_management.services.blogs;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.requests.comments.ReqCommentId;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.dtos.responses.categories.ResCategory;
import com.example.blog_management.dtos.responses.comments.ResComment;
import com.example.blog_management.dtos.responses.users.ResUser;
import com.example.blog_management.entities.Blog;
import com.example.blog_management.entities.Category;
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
public class BlogServiceImpl implements BlogService {
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
            List<Blog> blogs = blogRepo.findAll();
            List<ResBlog> result = new ArrayList<>();

            for (Blog blog : blogs) {
                ResBlog resBlog = modelMapper.map(blog, ResBlog.class);

                List<ResComment> comments = new ArrayList<>();
                for (Comment comment : blog.getComments()) {
                    ResComment resComment = modelMapper.map(comment, ResComment.class);

                    comments.add(resComment);
                }
                resBlog.setResComments(comments);

                ResUser user = modelMapper.map(blog.getUser(), ResUser.class);
                resBlog.setResUser(user);

                ResCategory category = modelMapper.map(blog.getCategory(), ResCategory.class);
                resBlog.setResCategory(category);

                result.add(resBlog);
            }

            restResponse.setMessage("Find all blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqBlogId reqBlogId) {
        RestResponse restResponse = new RestResponse();

        try {
            Blog blog = blogRepo.findById(reqBlogId.getId()).orElse(null);
            ResBlog result = modelMapper.map(blog, ResBlog.class);

            List<ResComment> comments = new ArrayList<>();
            for (Comment comment : blog.getComments()) {
                ResComment resComment = modelMapper.map(comment, ResComment.class);

                comments.add(resComment);
            }
            result.setResComments(comments);

            ResUser user = modelMapper.map(blog.getUser(), ResUser.class);
            result.setResUser(user);

            ResCategory category = modelMapper.map(blog.getCategory(), ResCategory.class);
            result.setResCategory(category);

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

            Category category = modelMapper.map(req.getReqCategoryId(), Category.class);

            Blog blog = modelMapper.map(req, Blog.class);

            blog.setCategory(category);

            // nếu tạo mới sẽ không lưu comments
            if (!update) {
                User user = modelMapper.map(req.getReqUserId(), User.class);
                blog.setUser(user);
            } else {
                List<Comment> comments = new ArrayList<>();
                for (ReqCommentId commentId : req.getComments()) {
                    Comment comment = modelMapper.map(commentId, Comment.class);
                    comments.add(comment);
                }
                blog.setComments(comments);
            }

            blogRepo.save(blog);
            ResBlog result = modelMapper.map(blog, ResBlog.class);

            List<ResComment> resComments = new ArrayList<>();
            for (Comment comment : blog.getComments()) {
                ResComment resComment = modelMapper.map(comment, ResComment.class);

                resComments.add(resComment);
            }
            result.setResComments(resComments);

            User user = userRepo.findById(blog.getUser().getId()).orElse(null);
            ResUser resUser = modelMapper.map(user, ResUser.class);
            result.setResUser(resUser);

            Category category1 = categoryRepo.findById(blog.getCategory().getId()).orElse(null);
            ResCategory resCategory = modelMapper.map(category1, ResCategory.class);
            result.setResCategory(resCategory);

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
    public RestResponse deleteById(ReqBlogId reqBlogId) {
        RestResponse restResponse = new RestResponse();

        try {
            blogRepo.deleteById(reqBlogId.getId());

            restResponse.setMessage("Delete blog success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse likeBlog(Integer blogId, Integer userId) {
        RestResponse restResponse = new RestResponse();

        try {
            // Tìm blog theo ID
            Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));

            // Tìm user theo ID
            User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Kiểm tra xem user đã like blog này chưa
            if (!blog.getLikedUsers().contains(user)) {
                // Nếu chưa like thì thêm user vào danh sách likedUsers
                blog.getLikedUsers().add(user);
                // Cập nhật lại số lượng liked
                blog.setLiked(blog.getLikedUsers().size());
                // Lưu lại vào cơ sở dữ liệu
                blogRepo.save(blog);
                restResponse.setMessage("Blog liked successfully");
            } else {
                restResponse.setMessage("User already liked this blog");
            }

            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to like blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse dislikeBlog(Integer blogId, Integer userId) {
        RestResponse restResponse = new RestResponse();

        try {
            // Tìm blog theo ID
            Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));

            // Tìm user theo ID
            User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Kiểm tra xem user đã like blog này chưa
            if (blog.getLikedUsers().contains(user)) {
                // Nếu đã like, bỏ user ra khỏi danh sách likedUsers
                blog.getLikedUsers().remove(user);
                // Cập nhật lại số lượng liked
                blog.setLiked(blog.getLikedUsers().size());
                // Lưu lại vào cơ sở dữ liệu
                blogRepo.save(blog);
                restResponse.setMessage("Blog disliked successfully");
            } else {
                restResponse.setMessage("User has not liked this blog yet");
            }

            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to dislike blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse searchTitle(String searchTitle) {
        RestResponse restResponse = new RestResponse();

        try {
            List<Blog> blogs = blogRepo.findByTitleContainingIgnoreCase(searchTitle);
            List<ResBlog> result = new ArrayList<>();

            for (Blog blog : blogs) {
                ResBlog resBlog = modelMapper.map(blog, ResBlog.class);

                List<ResComment> comments = new ArrayList<>();
                for (Comment comment : blog.getComments()) {
                    ResComment resComment = modelMapper.map(comment, ResComment.class);

                    comments.add(resComment);
                }
                resBlog.setResComments(comments);

                ResUser user = modelMapper.map(blog.getUser(), ResUser.class);
                resBlog.setResUser(user);

                ResCategory category = modelMapper.map(blog.getCategory(), ResCategory.class);
                resBlog.setResCategory(category);

                result.add(resBlog);
            }

            restResponse.setMessage("Find all blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse filterUser(ReqUserId reqUserId) {
        RestResponse restResponse = new RestResponse();

        try {
            User user = userRepo.findById(reqUserId.getId()).orElseThrow(() -> new RuntimeException("user is null"));
            List<Blog> blogs = blogRepo.findByUser(user);
            List<ResBlog> result = new ArrayList<>();

            for (Blog blog : blogs) {
                ResBlog resBlog = modelMapper.map(blog, ResBlog.class);

                List<ResComment> comments = new ArrayList<>();
                for (Comment comment : blog.getComments()) {
                    ResComment resComment = modelMapper.map(comment, ResComment.class);

                    comments.add(resComment);
                }
                resBlog.setResComments(comments);

                ResUser resUser = modelMapper.map(blog.getUser(), ResUser.class);
                resBlog.setResUser(resUser);

                ResCategory category = modelMapper.map(blog.getCategory(), ResCategory.class);
                resBlog.setResCategory(category);

                result.add(resBlog);
            }

            restResponse.setMessage("Find all blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse filterCategory(ReqCategoryId reqCategoryId) {
        RestResponse restResponse = new RestResponse();

        try {
            Category category = categoryRepo.findById(reqCategoryId.getId())
                    .orElseThrow(() -> new RuntimeException("Category is null"));
            List<Blog> blogs = blogRepo.findByCategory(category);
            List<ResBlog> result = new ArrayList<>();

            for (Blog blog : blogs) {
                ResBlog resBlog = modelMapper.map(blog, ResBlog.class);

                List<ResComment> comments = new ArrayList<>();
                for (Comment comment : blog.getComments()) {
                    ResComment resComment = modelMapper.map(comment, ResComment.class);

                    comments.add(resComment);
                }
                resBlog.setResComments(comments);

                ResUser resUser = modelMapper.map(blog.getUser(), ResUser.class);
                resBlog.setResUser(resUser);

                ResCategory resCategory = modelMapper.map(blog.getCategory(), ResCategory.class);
                resBlog.setResCategory(resCategory);

                result.add(resBlog);
            }

            restResponse.setMessage("Find all blog success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all blog: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
