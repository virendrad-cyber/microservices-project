package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserRequest;
import com.microservices.userservice.dto.UserResponse;
import com.microservices.userservice.entity.User;
import com.microservices.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse create(UserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return mapToResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
