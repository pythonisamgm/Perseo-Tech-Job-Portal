package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.IUserRepository;
import com.example.demo.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    IUserRepository iUserRepository;

    public User createUser(User user) {
        return iUserRepository.save(user);
    }

    public List<User> getAllUsers() {
        try {
            return iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users", e);
        }
    }

    public Optional<User> getUserById(long id) {
        try {
            return iUserRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by id", e);
        }
    }

    public User updateUser(User updatedUser) {
        Optional<User> existingUser = iUserRepository.findById(updatedUser.getUserId());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return iUserRepository.save(user);
        }
        return null;
    }

    public void deleteUserById(Long id) {
        iUserRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        iUserRepository.deleteAll();
    }

}

