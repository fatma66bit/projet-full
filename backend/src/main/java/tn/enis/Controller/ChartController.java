package tn.enis.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.enis.Entity.Quiz;
import tn.enis.Repo.QuizRepository;
import tn.enis.Entity.QuizResult;
import tn.enis.Repo.QuizResultRepository;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chart")
public class ChartController {
    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/user/{userId}")
    public List<Map<String, Object>> getUserQuizScores(@PathVariable Long userId) {
        List<QuizResult> results = quizResultRepository.findByUserId(userId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (QuizResult result : results) {
            Optional<Quiz> quizOpt = quizRepository.findById(result.getQuizId());
            if (quizOpt.isPresent()) {
                Quiz quiz = quizOpt.get();
                String topicName = quiz.getTopic().getName();
                Map<String, Object> item = new HashMap<>();
                item.put("quizId", quiz.getId()); // 👈 Ajout de l’ID du quiz
                item.put("topic", topicName);
                item.put("score", result.getScore());
                item.put("totalScore", result.getTotalScore());
                response.add(item);
            }
        }

        return response;
    }

}
