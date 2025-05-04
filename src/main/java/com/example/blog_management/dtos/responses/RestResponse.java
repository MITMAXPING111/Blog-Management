package com.example.blog_management.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private int status;
    private T data;
    private String message;
    private String messageError;
    private boolean success;
}
