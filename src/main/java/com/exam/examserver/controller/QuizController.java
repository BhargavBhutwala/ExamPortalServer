package com.exam.examserver.controller;

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

import com.exam.examserver.entity.exam.Quiz;
import com.exam.examserver.service.QuizService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //add the quiz
    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        return new ResponseEntity<>(this.quizService.addQuiz(quiz), HttpStatus.OK);
    }

    //update the quiz
    @PutMapping("/")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz){
        return new ResponseEntity<>(this.quizService.updateQuiz(quiz), HttpStatus.OK);
    }

    //get all quizzes
    @GetMapping("/")
    public ResponseEntity<?> getAllQuizzes(){
        return new ResponseEntity<>(this.quizService.getQuizzes(), HttpStatus.OK);
    }

    //get quiz by id
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable("quizId") long quizId) {
        return new ResponseEntity<>(this.quizService.getQuiz(quizId), HttpStatus.OK);
    }

    //delete the quiz
    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable("quizId") long quizId) {
        this.quizService.deleteQuiz(quizId);
    }
}
