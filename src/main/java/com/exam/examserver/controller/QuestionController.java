package com.exam.examserver.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.entity.exam.Question;
import com.exam.examserver.entity.exam.Quiz;
import com.exam.examserver.service.QuestionService;
import com.exam.examserver.service.QuizService;


@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    //add the question
    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.addQuestion(question), HttpStatus.OK);
    }

    //update the question
    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.updateQuestion(question), HttpStatus.OK);
    }

    //get all questions
    @GetMapping("/")
    public ResponseEntity<?> getAllQuestions() {
        return new ResponseEntity<>(this.questionService.getQuestions(), HttpStatus.OK);
    }

    //get question by id
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") long questionId){
        return new ResponseEntity<>(this.questionService.getQuestion(questionId), HttpStatus.OK);
    }

    //get questions of a quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quizId") long quizId){
        Quiz quiz = this.quizService.getQuiz(quizId);
        //return new ResponseEntity<>(this.questionService.getQuestionsOfQuiz(quiz), HttpStatus.OK);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList<>(questions);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions())+1);
        }
        Collections.shuffle(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/quiz/all/{quizId}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("quizId") long quizId){
        Quiz quiz = this.quizService.getQuiz(quizId);
        return new ResponseEntity<>(this.questionService.getQuestionsOfQuiz(quiz), HttpStatus.OK);
    }

    //delete a question by id
    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable("questionId") long questionId){
        this.questionService.deleteQuestion(questionId);
    }
}
