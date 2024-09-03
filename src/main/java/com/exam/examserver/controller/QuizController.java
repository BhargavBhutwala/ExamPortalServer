package com.exam.examserver.controller;

import java.util.Optional;
import java.util.Set;

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

import com.exam.examserver.entity.exam.Category;
import com.exam.examserver.entity.exam.Question;
import com.exam.examserver.entity.exam.Quiz;
import com.exam.examserver.service.CategoryService;
import com.exam.examserver.service.QuizService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    //add the quiz
    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        return new ResponseEntity<>(this.quizService.addQuiz(quiz), HttpStatus.OK);
    }

    //update the quiz
    @PutMapping("/")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz){
        
        //return new ResponseEntity<>(this.quizService.updateQuiz(quiz), HttpStatus.OK);
        // Fetch the existing quiz from the database
        Quiz existingQuiz = this.quizService.getQuiz(quiz.getqId());

        // If the quiz is not found, return a 404 response
        if (existingQuiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the existing quiz details
        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setDescription(quiz.getDescription());
        existingQuiz.setMaxMarks(quiz.getMaxMarks());
        existingQuiz.setNumberOfQuestions(quiz.getNumberOfQuestions());
        existingQuiz.setActive(quiz.isActive());
        existingQuiz.setCategory(quiz.getCategory());

        // Merge and update the questions
        Set<Question> updatedQuestions = quiz.getQuestions();
        Set<Question> existingQuestions = existingQuiz.getQuestions();

        for (Question updatedQuestion : updatedQuestions) {
            if (updatedQuestion.getQuestionId() == 0) {
                // New question, add it
                updatedQuestion.setQuiz(existingQuiz);
                existingQuestions.add(updatedQuestion);
            } else {
                // Existing question, find and update
                for (Question existingQuestion : existingQuestions) {
                    if (existingQuestion.getQuestionId() == updatedQuestion.getQuestionId()) {
                        existingQuestion.setContent(updatedQuestion.getContent());
                        existingQuestion.setOption1(updatedQuestion.getOption1());
                        existingQuestion.setOption2(updatedQuestion.getOption2());
                        existingQuestion.setOption3(updatedQuestion.getOption3());
                        existingQuestion.setOption4(updatedQuestion.getOption4());
                        existingQuestion.setAnswer(updatedQuestion.getAnswer());
                    }
                }
            }
        }

        // Save the updated quiz
        return new ResponseEntity<>(this.quizService.updateQuiz(existingQuiz), HttpStatus.OK);
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

    @GetMapping("/category/{cId}")
    public ResponseEntity<?> getQuizzesByCategory(@PathVariable("cId") long cId) {
        Optional<Category> category = this.categoryService.getCategory(cId);
        return new ResponseEntity<>(this.quizService.getQuizzesByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveQuizzes() {
        return new ResponseEntity<>(this.quizService.getActiveQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/category/active/{cId}")
    public ResponseEntity<?> getActiveQuizzesByCategory(@PathVariable("cId") long cId) {
        Optional<Category> category = this.categoryService.getCategory(cId);
        return new ResponseEntity<>(this.quizService.getActiveQuizzesByCategory(category), HttpStatus.OK);
    }
}
