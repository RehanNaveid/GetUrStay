package com.example.MegLogX.Service;


import com.example.MegLogX.Dtos.UserRequest;
import com.example.MegLogX.Dtos.UserResponse;
import com.example.MegLogX.Entity.UserEntity;
import com.example.MegLogX.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(); // You can also inject this bean

    public UserResponse registerUser(UserRequest request) {
        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        return mapToResponse(user);
    }

    public UserResponse getUserById(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        return userOpt.map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        UserEntity updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }


    // 🔁 Utility method to convert Entity to Response DTO
    private UserResponse mapToResponse(UserEntity user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
}
}

