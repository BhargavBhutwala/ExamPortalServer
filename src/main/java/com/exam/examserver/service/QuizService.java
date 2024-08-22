package com.exam.examserver.service;

import java.util.Set;

import com.exam.examserver.entity.exam.Quiz;

public interface QuizService {

    public Quiz addQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getQuizzes();

    public Quiz getQuiz(long id);

    public void deleteQuiz(long id);

}
