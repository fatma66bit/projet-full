package tn.enis;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    private final QuizResultRepository quizResultRepository;

    public QuizService(QuizRepository quizRepository,
                       TopicRepository topicRepository,
                       QuestionRepository questionRepository,
                       QuizResultRepository quizResultRepository) {
        this.quizRepository = quizRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.quizResultRepository = quizResultRepository;
    }


    public Quiz generateQuiz(Long topicId, List<Question> questions) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Quiz quiz = new Quiz();
        quiz.setTopic(topic);
        quiz.setCreatedAt(LocalDateTime.now());

        // lier les questions au quiz
        for (Question q : questions) {
            q.setQuiz(quiz);
        }

        quiz.setQuestions(questions);

        return quizRepository.save(quiz); // Cascade = ALL => questions enregistrées automatiquement
    }
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
    public QuizResult calculateQuizResult(Long quizId) {
        Quiz quiz = getQuizById(quizId);
        int score = 0;
        int total = quiz.getQuestions().size();

        for (Question q : quiz.getQuestions()) {
            if (q.getUserResponse() != null && q.getUserResponse().equals(q.getCorrectAnswer())) {
                score++;
            }
        }

        Long userId = quiz.getTopic().getUser().getId();

        QuizResult result = new QuizResult(quizId, userId, score, total);
        return quizResultRepository.save(result);
    }

    public boolean deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            return false;
        }
        quizRepository.deleteById(id);
        return true;
    }
    public QuizResult saveQuizResult(QuizResult result) {
        return quizResultRepository.save(result);
    }


    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz quiz = getQuizById(id);

        quiz.setTopic(updatedQuiz.getTopic());
        quiz.setCreatedAt(updatedQuiz.getCreatedAt());

        // Détacher anciennes questions et ajouter les nouvelles (simple exemple)
        if (updatedQuiz.getQuestions() != null) {
            for (Question q : updatedQuiz.getQuestions()) {
                q.setQuiz(quiz);
            }
            quiz.setQuestions(updatedQuiz.getQuestions());
        }

        return quizRepository.save(quiz);
    }
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

}
