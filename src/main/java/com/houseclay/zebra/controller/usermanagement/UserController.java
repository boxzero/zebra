package com.houseclay.zebra.controller.usermanagement;

import com.houseclay.zebra.dto.UserDTO;
import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.service.UserService;

import com.houseclay.zebra.utils.ApplicationConfig;
import com.houseclay.zebra.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Api(tags = "User Management")
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ApplicationConfig applicationConfig;

    @ApiOperation(value = "Get all Users of Zebra",response = User.class)
    @GetMapping("/v1/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }


    @ApiOperation(value="Fetch an user by id",response = UserDTO.class)
    @GetMapping("/v1/{id}")
    public ResponseEntity<UserDTO> fetchUser(@PathVariable("id") String user_uuid) {
        System.out.println("ID::: "+user_uuid);
        UserDTO userDTO = userService.findById(UUID.fromString(user_uuid));
        if(userDTO == null)
        {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok().body(userDTO);

    }

    /**
     * Create a new user
     * @param user
     * @return
     */
    @ApiOperation(value = "Register a new user for zebra",response = User.class)
    @PostMapping("/v1/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils(applicationConfig);
        String username = jwtTokenUtils.extractUsernamefromToken(token);
        System.out.println("Logged in user::::: "+username);
        return ResponseEntity.ok().body(userService.registerUser(user,username));
    }

    @PostMapping("v1/register-role")
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Role> registerRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
}
