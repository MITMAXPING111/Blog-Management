package com.example.blog_management.services.categories;

import com.example.blog_management.dtos.requests.categories.ReqCategory;
import com.example.blog_management.dtos.requests.categories.ReqCategoryId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.categories.ResCategory;
import com.example.blog_management.entities.Category;
import com.example.blog_management.repositories.BlogRepo;
import com.example.blog_management.repositories.CategoryRepo;
import com.example.blog_management.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    BlogRepo blogRepo;
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
            List<Category> categories = categoryRepo.findAll();
            List<ResCategory> result = new ArrayList<>();
            for (Category category : categories) {
                ResCategory resCategory = modelMapper.map(category, ResCategory.class);

                result.add(resCategory);
            }

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
    public RestResponse findById(ReqCategoryId reqCategoryId) {
        RestResponse restResponse = new RestResponse();

        try {
            Category category = categoryRepo.findById(reqCategoryId.getId()).orElse(null);
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
            Category category;
            // nếu update thì bỏ qua blogs, chỉ update các thông tin khác
            if (req.getId() != null && categoryRepo.existsById(req.getId())) {
                category = categoryRepo.findById(req.getId()).orElse(null);

                req.setCreateAt(category.getCreateAt());
                req.setCreateBy(category.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");

                update = true;
            } else {
                category = modelMapper.map(req, Category.class);

                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            categoryRepo.save(category);
            ResCategory result = modelMapper.map(category, ResCategory.class);

            restResponse.setMessage(update ? "Update category success" : "Create category success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update category: " + e.getMessage())
                    : ("Failed to create category: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqCategoryId reqCategoryId) {
        RestResponse restResponse = new RestResponse();

        try {
            blogRepo.deleteById(reqCategoryId.getId());

            restResponse.setMessage("Delete category success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete category: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
