package com.exam.examserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.examserver.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

    public User findByUsername(String username);

    public void deleteByUsername(String username);

}
