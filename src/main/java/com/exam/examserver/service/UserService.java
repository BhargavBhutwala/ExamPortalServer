package com.exam.examserver.service;

import java.util.Set;

import com.exam.examserver.entity.User;
import com.exam.examserver.entity.UserRole;

public interface UserService {

    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    public User getUser(String username);

    public void deleteUser(String username);

}
