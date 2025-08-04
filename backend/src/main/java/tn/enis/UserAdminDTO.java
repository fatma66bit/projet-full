package tn.enis;

import java.util.List;

public class UserAdminDTO {
    public Long id;
    public String fullName;
    public String email;
    public String role;
    public List<String> topicNames;
    public List<QuizResultDTO> quizResults;
}
