package com.example.jormcrud.api;

import java.lang.Integer;
import java.lang.String;

public record UserResponse(
        Integer id,
        String name,
        String email
) {}
