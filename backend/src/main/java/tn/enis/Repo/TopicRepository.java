package tn.enis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Entity.Topic;
import tn.enis.Entity.User;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    void deleteAllByUser(User user);
}
