package com.example.jormcrud.db;

import java.lang.Integer;
import java.lang.String;

public record AppUser(
        Integer id,
        String email,
        String name
) {}
