package com.example.blog_management.services.categories;

import com.example.blog_management.dtos.requests.categories.ReqCategory;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.responses.RestResponse;

public interface CategoryService {
    RestResponse findAll();

    RestResponse findById(ReqCategoryId reqCategoryId);

    RestResponse createOrUpdate(ReqCategory req);

    RestResponse deleteById(ReqCategoryId categoryId);

}
