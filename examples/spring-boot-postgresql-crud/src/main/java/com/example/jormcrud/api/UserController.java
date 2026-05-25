package com.example.jormcrud.api;

import com.example.jormcrud.db.AppUser;
import com.example.jormcrud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> list() {
        return service.list().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Integer id) {
        return toResponse(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserCreateRequest request) {
        return toResponse(service.create(request));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request) {
        return toResponse(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(user.id(), user.name(), user.email());
    }
}
