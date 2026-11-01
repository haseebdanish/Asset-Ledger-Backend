package com.haseeb.assetledger.Service;


import com.haseeb.assetledger.Dto.UserRequestDto;
import com.haseeb.assetledger.Dto.UserResponseDto;
import com.haseeb.assetledger.Exception.EmailAlreadyExistsException;
import com.haseeb.assetledger.Exception.UserNotFoundException;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserRequestDto request) {

        String normalizedEmail = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        User user = new User();
        user.setUserName(request.userName().trim());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        return new UserResponseDto(
                saved.getUserId(),
                saved.getUserName(),
                saved.getEmail()
        );
    }

    public UserResponseDto getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail()
        );
    }

    public UserResponseDto getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}
