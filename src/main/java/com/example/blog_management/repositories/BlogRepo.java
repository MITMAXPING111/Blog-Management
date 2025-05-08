package com.example.blog_management.repositories;

import com.example.blog_management.entities.Blog;
import com.example.blog_management.entities.Category;
import com.example.blog_management.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {
    // Tìm kiếm theo tiêu đề, không phân biệt chữ hoa chữ thường
    List<Blog> findByTitleContainingIgnoreCase(String searchTitle);

    // Lọc bài viết theo tác giả
    List<Blog> findByUser(User user);

    // Lọc bài viết theo danh mục
    List<Blog> findByCategory(Category category);
}
