package com.example.blog_management.services.categories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blog_management.dtos.requests.categories.ReqCategory;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.categories.ResCategory;
import com.example.blog_management.entities.Category;
import com.example.blog_management.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Category> categories = categoryRepo.findAll();
            List<ResCategory> result = new ArrayList<>();

            for (Category p : categories) {
                ResCategory resCategory = modelMapper.map(p, ResCategory.class);

                result.add(resCategory);
            }

            restResponse.setMessage("Find all Category success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all Category: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqCategoryId categoryId) {
        RestResponse restResponse = new RestResponse();

        try {
            Category category = categoryRepo.findById(categoryId.getId()).orElse(null);
            ResCategory result = modelMapper.map(category, ResCategory.class);

            restResponse.setMessage("Find category success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get category: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqCategory req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if (req.getId() != null && categoryRepo.existsById(req.getId())) {
                Category category = categoryRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(category.getCreateAt());
                req.setCreateBy(category.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else {
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            Category category = modelMapper.map(req, Category.class);
            categoryRepo.save(category);
            ResCategory result = modelMapper.map(category, ResCategory.class);

            restResponse.setMessage(update ? "Update category success" : "Create category success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update categories: " + e.getMessage())
                    : ("Failed to create categrories: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqCategoryId categoryId) {
        RestResponse restResponse = new RestResponse();

        try {
            categoryRepo.deleteById(categoryId.getId());

            restResponse.setMessage("Delete categories success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete categories: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
