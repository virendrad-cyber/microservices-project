package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserRequest;
import com.microservices.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}
