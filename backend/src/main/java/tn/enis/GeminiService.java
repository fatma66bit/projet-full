package tn.enis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent")

                .build();
    }

    public String generateText(String prompt) {
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                    if (candidates != null && !candidates.isEmpty()) {
                        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                        return parts.get(0).get("text");
                    }
                    return "Aucune réponse générée.";
                })
                .block();
    }
    public List<Question> generateQuizQuestions(String topicName, int questionCount) {
        String prompt = String.format("""
        Génère %d questions à choix multiple sur le sujet suivant : %s.
        Format : 
        Q: <question> 
        Options: a) <option1> b) <option2> c) <option3> d) <option4> 
        Réponse: <lettre correcte>
        """, questionCount, topicName);

        String result = generateText(prompt);
        return parseQuestions(result);
    }

    private List<Question> parseQuestions(String text) {
        List<Question> questions = new ArrayList<>();

        String[] blocks = text.split("(?=Q:)");

        for (String block : blocks) {
            try {
                String[] lines = block.trim().split("\n");
                String questionText = lines[0].replace("Q:", "").trim();

                List<String> options = new ArrayList<>();
                String correctAnswerLetter = null;
                for (String line : lines) {
                    if (line.toLowerCase().startsWith("options")) {
                        String[] parts = line.split("(?=[a-dA-D]\\))");
                        for (String opt : parts) {
                            if (opt.contains(")")) {
                                options.add(opt.replaceAll("[a-dA-D]\\)", "").trim());
                            }
                        }
                    } else if (line.toLowerCase().startsWith("réponse")) {
                        correctAnswerLetter = line.replaceAll("[^a-dA-D]", "").toLowerCase();
                    }
                }

                int correctIndex = "abcd".indexOf(correctAnswerLetter);
                String correctAnswer = (correctIndex >= 0 && correctIndex < options.size()) ? options.get(correctIndex) : null;

                if (questionText != null && options.size() == 4 && correctAnswer != null) {
                    Question q = new Question();
                    q.setQuestionText(questionText);
                    q.setOptions(options);
                    q.setCorrectAnswer(correctAnswer);
                    questions.add(q);
                }
            } catch (Exception e) {
                // Ignore malformed block
            }
        }

        return questions;
    }


}
