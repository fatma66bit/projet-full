package tn.enis.Service;

import org.springframework.stereotype.Service;
import tn.enis.Entity.Topic;
import tn.enis.Repo.TopicRepository;
import tn.enis.Entity.User;
import tn.enis.Repo.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, GeminiService geminiService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.geminiService = geminiService;
    }

    public Topic learn(String topicName, Long userId) {
        // 1. Appels à Gemini pour générer contenu
        String summaryPrompt = "Donne un résumé clair et concis du sujet suivant : " + topicName;
        String summary = geminiService.generateText(summaryPrompt);

        String definitionsPrompt = "Donne 3 termes clés du sujet \"" + topicName +
                "\" avec leurs définitions, au format : terme1: définition1, terme2: définition2, terme3: définition3.";
        String definitionsText = geminiService.generateText(definitionsPrompt);

        Map<String, String> definitions = parseDefinitions(definitionsText);
        System.out.println("Définitions générées par Gemini : " + definitionsText);

        // 2. Enregistrement du topic SANS ces champs
        User user = userRepository.findById(userId).orElseThrow();
        Topic topic = new Topic();
        topic.setName(topicName);
        topic.setUser(user);

        topicRepository.save(topic); // persisté sans summary ni definitions

        // 3. Remplir manuellement les champs @Transient
        topic.setSummary(summary);
        topic.setDefinitions(definitions);

        return topic; // ✅ on retourne la version enrichie du Topic
    }

    private Map<String, String> parseDefinitions(String text) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = text.split(",");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length == 2) {
                map.put(parts[0].trim(), parts[1].trim());
            }
        }
        return map;
    }
    public Topic updateTopic(Long topicId, Topic updatedTopic) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + topicId));

        topic.setName(updatedTopic.getName());
        topic.setDefinitions(updatedTopic.getDefinitions());
        topic.setSummary(updatedTopic.getSummary());
        // On ne change pas l'utilisateur ici, mais tu peux si besoin
        return topicRepository.save(topic);
    }

    // Méthode pour supprimer un topic par id
    public void deleteTopic(Long topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new RuntimeException("Topic not found with id " + topicId);
        }
        topicRepository.deleteById(topicId);
    }
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
