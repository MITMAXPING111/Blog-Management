package com.example.blog_management.services.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blog_management.dtos.requests.blogs.ReqBlog;
import com.example.blog_management.dtos.requests.users.ReqUser;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.blogs.ResBlog;
import com.example.blog_management.dtos.responses.users.ResUser;
import com.example.blog_management.entities.Blog;
import com.example.blog_management.entities.User;
import com.example.blog_management.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<User> users = userRepo.findAll();
            List<ResUser> result = new ArrayList<>();

            for (User user : users) {
                ResUser resUser = modelMapper.map(user, ResUser.class);
                result.add(resUser);
            }

            restResponse.setMessage("Successfully retrieved all users");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to retrieve users: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqUserId reqUserId) {
        RestResponse restResponse = new RestResponse();

        if (reqUserId == null || reqUserId.getId() == null) {
            restResponse.setMessage("Invalid user ID");
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return restResponse;
        }

        try {
            Optional<User> userOptional = userRepo.findById(reqUserId.getId());
            if (userOptional.isEmpty()) {
                restResponse.setMessage("User not found");
                restResponse.setSuccess(false);
                restResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return restResponse;
            }

            ResUser result = modelMapper.map(userOptional.get(), ResUser.class);
            restResponse.setMessage("Successfully retrieved user");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to retrieve user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqUser req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if (req.getId() != null && userRepo.existsById(req.getId())) {
                User user = userRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(user.getCreateAt());
                req.setCreateBy(user.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());

                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else {
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            User user = modelMapper.map(req, User.class);
            userRepo.save(user);
            ResUser result = modelMapper.map(user, ResUser.class);

            restResponse.setMessage(update ? "Update user success" : "Create user success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update users: " + e.getMessage())
                    : ("Failed to create user: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqUserId userId) {
        RestResponse restResponse = new RestResponse();

        if (userId == null || userId.getId() == null) {
            restResponse.setMessage("Invalid user ID");
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return restResponse;
        }

        try {
            if (!userRepo.existsById(userId.getId())) {
                restResponse.setMessage("User not found");
                restResponse.setSuccess(false);
                restResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return restResponse;
            }

            userRepo.deleteById(userId.getId());
            restResponse.setMessage("Successfully deleted user");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return restResponse;
    }
}