package tn.enis;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteAllByQuiz(Quiz quiz);
}
