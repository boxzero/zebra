package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.repository.RoleRepository;
import com.houseclay.zebra.repository.UserRepository;
import com.houseclay.zebra.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public String loginUser(String username, String password) {
        return null;
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Optional<User> user= Optional.ofNullable(userRepository.findByUsername(userName));

        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
