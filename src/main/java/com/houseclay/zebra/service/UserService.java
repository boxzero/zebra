package com.houseclay.zebra.service;

import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public String loginUser(String username,String password);
    public User registerUser(User user); // this is to save user
    public List<User> findAll(); //make is pageable - 10 users at once
    public Optional<User> findByUserName(String userName);
    public Optional<User> findById(String id);
    public void addRoleToUser(String username, String roleName);
    public Role saveRole(Role role);
}
;