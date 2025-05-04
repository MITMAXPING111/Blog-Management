package com.example.blog_management.services.comment;

import com.example.blog_management.dtos.requests.comments.ReqComment;
import com.example.blog_management.dtos.requests.comments.ReqCommentId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface CommentService {
    RestResponse findAll();

    RestResponse findById(ReqCommentId reqCommentId);

    RestResponse createOrUpdate(ReqComment req);

    RestResponse deleteById(ReqCommentId commentId);

}
