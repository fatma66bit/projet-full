package tn.enis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.enis.Entity.Quiz;
import tn.enis.Entity.User;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("DELETE FROM Quiz q WHERE q.topic IN (SELECT t FROM Topic t WHERE t.user = :user)")
    @Modifying
    void deleteAllByUser(@Param("user") User user);
}
