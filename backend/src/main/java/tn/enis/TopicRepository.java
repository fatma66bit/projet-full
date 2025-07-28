package tn.enis;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    void deleteAllByUser(User user);
}
