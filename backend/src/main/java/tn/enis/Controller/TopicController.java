package tn.enis.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.enis.Entity.Topic;
import tn.enis.Service.TopicService;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/learn")
    public ResponseEntity<Topic> learnTopic(@RequestBody Map<String, String> request) {
        String topicName = request.get("topic");
        Long userId = Long.parseLong(request.get("userId")); // on suppose que tu envoies aussi l'id utilisateur

        Topic topic = topicService.learn(topicName, userId);
        return ResponseEntity.ok(topic);
    }
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Topic topic = topicService.updateTopic(id, updatedTopic);
        return ResponseEntity.ok(topic);
    }

    // DELETE topic by id
    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

}
