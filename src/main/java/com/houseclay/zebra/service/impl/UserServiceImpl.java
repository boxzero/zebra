package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.dto.UserDTO;
import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.repository.RoleRepository;
import com.houseclay.zebra.repository.UserRepository;
import com.houseclay.zebra.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null) {
            log.error("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }else {
            log.info("User Found in the database: {}",username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public String loginUser(String username, String password) {
        return null;
    }

    @Override
    public User registerUser(User user, String loggedInUser) {
        log.info("Saving new user {} to the database",user);
        user.setBaseTimeStamp(BaseTimeStamp.builder().created_by(loggedInUser).created_on(new Date()).build());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public UserDTO findById(UUID id) {
        return userRepository.findById(id).isPresent()? this.maptoUserDTO(userRepository.findById(id).get()) : null;
    }

    private UserDTO maptoUserDTO(User user) {

        return UserDTO.builder().
                username(user.getUsername()).firstName(user.getFirstName()).lastName(user.getLastName())
                        .contactNumber(user.getContactNumber()).active(user.isActive())
                        .isEmailVerified(user.isEmailVerified()).isPhoneVerfied(user.isPhoneVerified())
                        .notes(user.getNotes()).baseTimeStamp(user.getBaseTimeStamp())
                .build();
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
