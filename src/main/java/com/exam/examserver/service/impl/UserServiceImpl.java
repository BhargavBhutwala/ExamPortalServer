package com.exam.examserver.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examserver.entity.User;
import com.exam.examserver.entity.UserRole;
import com.exam.examserver.repository.RoleRepository;
import com.exam.examserver.repository.UserRepository;
import com.exam.examserver.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        
        User userLocal = this.userRepository.findByUsername(user.getUsername());
        if (userLocal!=null) {
            System.out.println("User already exists!");
            throw new Exception("User already exists!");
        } else {
            //create a new user
            for(UserRole ur: userRoles){
                this.roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            userLocal=this.userRepository.save(user);
        }
        return userLocal;
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(String username) {
        this.userRepository.deleteByUsername(username);
    }

}
