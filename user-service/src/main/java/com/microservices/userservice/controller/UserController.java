package com.microservices.userservice.controller;

import com.microservices.userservice.dto.UserRequest;
import com.microservices.userservice.dto.UserResponse;
import com.microservices.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @RequestBody UserRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
