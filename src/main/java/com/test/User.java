package com.test;

import java.time.LocalDateTime;
import java.util.List;

public record User(
    Integer id,
    String name,
    String email
) {}
