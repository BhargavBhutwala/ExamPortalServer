package com.exam.examserver.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examserver.entity.exam.Question;
import com.exam.examserver.entity.exam.Quiz;
import com.exam.examserver.repository.QuestionRepository;
import com.exam.examserver.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        
        Set<Question> questionSet = new LinkedHashSet<>();

        for(Question question : this.questionRepository.findAll()){
            questionSet.add(question);
        }

        return questionSet;
    }

    @Override
    public Question getQuestion(long id) {
        return this.questionRepository.findById(id).get();
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(long id) {
        this.questionRepository.deleteById(id);
    }

}
