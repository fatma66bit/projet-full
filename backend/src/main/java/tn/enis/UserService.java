package tn.enis;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       TopicRepository topicRepository,
                       QuizRepository quizRepository,
                       QuizResultRepository quizResultRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.quizRepository = quizRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> login(String fullName, String password) {
        return userRepository.findByFullNameAndPassword(fullName, password);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        });
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Supprimer les résultats de quiz d'abord
            quizResultRepository.deleteAllByUserId(user.getId());

            // Supprimer les quiz (qui dépendent des topics)
            quizRepository.deleteAllByUser(user);

            // Supprimer les topics ensuite
            topicRepository.deleteAllByUser(user);

            // Supprimer l'utilisateur
            userRepository.delete(user);
            return true;
        }
        return false;
    }

}
