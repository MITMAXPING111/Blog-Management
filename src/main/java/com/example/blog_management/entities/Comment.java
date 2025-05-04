package com.example.blog_management.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.blog_management.dtos.requests.blogs.ReqBlogId;
import com.example.blog_management.dtos.requests.users.ReqUserId;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String content;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

}
