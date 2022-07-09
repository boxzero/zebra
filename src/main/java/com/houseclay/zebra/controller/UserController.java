package com.houseclay.zebra.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

@RestController
@Api(tags = "User Management")
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Get all Users of Zebra",response = User.class)
    @GetMapping("/v1/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }


    @ApiOperation(value = "Register a new user for zebra",response = User.class)
    @PostMapping("/v1/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.registerUser(user));
    }

    @PostMapping("v1/register-role")
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Role> registerRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
}
