package com.example.demo.controllers;

import com.example.demo.dto.user.UserConverter;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.models.User;
import com.example.demo.services.UserServiceImpl;
import com.example.demo.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final UserConverter userConverter;


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        User user = userConverter.dtoToUser(userDTO);
        User createdUser = userService.createUser(user);
        UserDTO createdUserDTO = userConverter.userToDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(userConverter::userToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            UserDTO userDTO = userConverter.userToDto(userOpt.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userConverter.dtoToUser(userDTO);
        user.setUserId(id);
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = userConverter.userToDto(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }
}
