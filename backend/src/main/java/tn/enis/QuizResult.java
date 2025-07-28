package tn.enis;

import jakarta.persistence.*;

@Entity
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private int totalScore;

    private Long userId;

    private Long quizId;

    public QuizResult() {
    }

    public QuizResult(Long quizId, Long userId, int score, int totalScore) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.totalScore = totalScore;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
