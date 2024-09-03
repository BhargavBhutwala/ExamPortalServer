package com.exam.examserver.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.exam.examserver.entity.exam.Category;
import com.exam.examserver.entity.exam.Quiz;

public interface QuizService {

    public Quiz addQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getQuizzes();

    public Quiz getQuiz(long id);

    public void deleteQuiz(long id);

    public List<Quiz> getQuizzesByCategory(Optional<Category> category);

    public List<Quiz> getActiveQuizzes();

    public List<Quiz> getActiveQuizzesByCategory(Optional<Category> category);

}
