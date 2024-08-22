package com.exam.examserver.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examserver.entity.exam.Quiz;
import com.exam.examserver.repository.QuizRepository;
import com.exam.examserver.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        
        Set<Quiz> quizSet = new LinkedHashSet<>();

        for(Quiz q: this.quizRepository.findAll()){
            quizSet.add(q);
        }

        return quizSet;
    }

    @Override
    public Quiz getQuiz(long id) {
        return this.quizRepository.findById(id).get();
    }

    @Override
    public void deleteQuiz(long id) {
        this.quizRepository.deleteById(id);
    }

}
