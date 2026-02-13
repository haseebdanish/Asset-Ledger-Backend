package com.haseeb.assetledger.Service;


import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User getUserById(Long userid) {
        return userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
