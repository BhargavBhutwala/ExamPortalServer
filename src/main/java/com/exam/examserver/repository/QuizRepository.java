package com.exam.examserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.examserver.entity.exam.Category;
import com.exam.examserver.entity.exam.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long>{

    public List<Quiz> findByCategory(Optional<Category> category);

}
