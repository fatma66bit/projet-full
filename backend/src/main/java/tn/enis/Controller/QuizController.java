package tn.enis.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.enis.Entity.Question;
import tn.enis.Entity.Quiz;
import tn.enis.Entity.QuizResult;
import tn.enis.Entity.Topic;
import tn.enis.Service.GeminiService;
import tn.enis.Service.QuizService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // ✅ car React tourne sur 5173
 // si tu travailles avec Angular
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final GeminiService geminiService;

    public QuizController(QuizService quizService, GeminiService geminiService) {
        this.quizService = quizService;
        this.geminiService = geminiService;
    }
    @PostMapping("/generate/auto")
    public ResponseEntity<Quiz> generateAutoQuiz(
            @RequestParam Long topicId
            // Suppression de questionCount en paramètre
    ) {
        int questionCount = 5; // forcer à 5

        Topic topic = quizService.getTopicById(topicId);
        List<Question> questions = geminiService.generateQuizQuestions(topic.getName(), questionCount);
        Quiz quiz = quizService.generateQuiz(topicId, questions);
        return ResponseEntity.ok(quiz);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }
    @PostMapping("/{id}/submit")
    public ResponseEntity<QuizResult> submitQuiz(
            @PathVariable Long id,
            @RequestBody List<String> userResponses
    ) {
        Quiz quiz = quizService.getQuizById(id);
        List<Question> questions = quiz.getQuestions();

        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String userAnswer = (i < userResponses.size()) ? userResponses.get(i) : null;
            q.setUserResponse(userAnswer);

            if (userAnswer != null && userAnswer.equals(q.getCorrectAnswer())) {
                score++;
            }
        }

        Long userId = quiz.getTopic().getUser().getId();
        QuizResult result = new QuizResult(id, userId, score, questions.size());
        QuizResult savedResult = quizService.saveQuizResult(result);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deleted = quizService.deleteQuiz(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        Quiz quiz = quizService.updateQuiz(id, updatedQuiz);
        return ResponseEntity.ok(quiz);
    }
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }


}
