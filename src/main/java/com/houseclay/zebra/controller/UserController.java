package com.houseclay.zebra.controller;

import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Get all Users of Zebra",response = User.class)
    @GetMapping("/v1/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }
    @ApiOperation(value = "Register a new user for zebra",response = String.class)
    @PostMapping("v1/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.registerUser(user));
    }

    @PostMapping("v1/register-role")
    public ResponseEntity<Role> registerRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
}
