package tn.enis;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    void deleteAllByUserId(Long userId);
}
