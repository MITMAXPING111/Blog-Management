package com.example.blog_management.services.user;

import com.example.blog_management.dtos.requests.users.ReqUser;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface UserService {
    RestResponse findAll();

    RestResponse findById(ReqUserId reqUserId);

    RestResponse createOrUpdate(ReqUser req);

    RestResponse deleteById(ReqUserId userId);

}
