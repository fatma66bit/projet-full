package tn.enis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Entity.QuizResult;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    void deleteAllByUserId(Long userId);

    List<QuizResult> findByUserId(Long userId);
}
