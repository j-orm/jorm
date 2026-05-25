package com.example.jormcrud.service;

import com.example.jormcrud.api.UserCreateRequest;
import com.example.jormcrud.api.UserUpdateRequest;
import com.example.jormcrud.db.AppUser;
import com.example.jormcrud.db.Jorm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final Jorm jorm;

    public UserService(Jorm jorm) {
        this.jorm = jorm;
    }

    public List<AppUser> list() {
        return jorm.appUser().findMany();
    }

    public AppUser getById(Integer id) {
        AppUser user = jorm.appUser().findById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    public AppUser create(UserCreateRequest request) {
        ensureEmailAvailable(request.email(), null);

        jorm.appUser().create(d -> d
                .name(request.name())
                .email(request.email())
        );

        List<AppUser> created = jorm.appUser().findMany(w -> w.email().equals(request.email()));
        if (created.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User was not created");
        }
        return created.get(0);
    }

    public AppUser update(Integer id, UserUpdateRequest request) {
        getById(id);
        ensureEmailAvailable(request.email(), id);

        AppUser updated = jorm.appUser().update(id, d -> d
                .name(request.name())
                .email(request.email())
        );

        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return updated;
    }

    public void delete(Integer id) {
        getById(id);
        jorm.appUser().delete(id);
    }

    private void ensureEmailAvailable(String email, Integer currentUserId) {
        List<AppUser> existing = jorm.appUser().findMany(w -> w.email().equals(email));
        if (existing.isEmpty()) {
            return;
        }

        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }

        AppUser other = existing.get(0);
        if (!currentUserId.equals(other.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }
    }
}
