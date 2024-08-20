package com.exam.examserver.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.entity.Role;
import com.exam.examserver.entity.User;
import com.exam.examserver.entity.UserRole;
import com.exam.examserver.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //creating a new user
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {

        user.setProfile("default.png");

        //encoding password with Bcrypt password encoder
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Set<UserRole> userRoles = new HashSet<>();

        UserRole userRole = new UserRole();

        Role role = new Role();
        role.setId(1L); 
        role.setRoleName("Normal");

        userRole.setUser(user);
        userRole.setRole(role);

        userRoles.add(userRole);

        return new ResponseEntity<>(this.userService.createUser(user, userRoles), HttpStatus.OK);
    }

    //getting a user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        return new ResponseEntity<>(this.userService.getUser(username), HttpStatus.OK);
    }

    //deleting a user by username
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username){
        this.userService.deleteUser(username);
    }

}
