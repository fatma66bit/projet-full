package tn.enis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Entity.Question;
import tn.enis.Entity.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteAllByQuiz(Quiz quiz);
}
