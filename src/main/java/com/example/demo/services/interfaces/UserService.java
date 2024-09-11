package com.example.demo.services.interfaces;

import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);

    public List<User> getAllUsers();

    public Optional<User> getUserById(long id);

    public User updateUser(User updatedUser);

    public void deleteUserById(Long id);

    public void deleteAllUsers();

}
