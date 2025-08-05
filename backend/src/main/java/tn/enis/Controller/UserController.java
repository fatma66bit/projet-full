package tn.enis.Controller;
import tn.enis.*;
import tn.enis.Dto.QuizResultDTO;

import tn.enis.Dto.UserAdminDTO;
import tn.enis.Dto.UserDTO;
import tn.enis.Entity.Topic;
import tn.enis.Entity.User;
import tn.enis.Mapper.UserMapper;
import tn.enis.Repo.QuizResultRepository;
import tn.enis.Repo.UserRepository;
import tn.enis.Service.UserService;
import tn.enis.jwt.JwtUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final QuizResultRepository quizResultRepository;

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository, QuizResultRepository quizResultRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.quizResultRepository = quizResultRepository;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        // Si le rôle n'est pas fourni, on définit un rôle par défaut
        if (user.getRole() == null) {
            user.setRole(Role.USER); // rôle par défaut
        }

        User savedUser = userService.createUser(user);
        return UserMapper.toDto(savedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping("/loginClient")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO loginDto) {
        String fullName = loginDto.getFullName();
        String password = loginDto.getPassword();

        Optional<User> userOpt = userService.login(fullName, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getId(), fullName, password);

            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "userId", user.getId(),
                    "role", user.getRole().name() // ✅ Inclure le rôle
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/fullName")
    public ResponseEntity<Map<String, String>> getFullName(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(Map.of("fullName", user.getFullName())))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Utilisateur non trouvé")));
    }
    @GetMapping("/admin/users-info")
    public List<UserAdminDTO> getAllUsersWithDetails() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserAdminDTO dto = new UserAdminDTO();
            dto.id = user.getId();
            dto.fullName = user.getFullName();
            dto.email = user.getEmail();
            dto.role = user.getRole().name();
            dto.topicNames = user.getTopics().stream()
                    .map(Topic::getName).toList();
            dto.quizResults = quizResultRepository.findByUserId(user.getId()).stream().map(qr -> {
                QuizResultDTO q = new QuizResultDTO();
                q.quizId = qr.getQuizId();
                q.score = qr.getScore();
                q.totalScore = qr.getTotalScore();
                return q;
            }).toList();

            return dto;
        }).toList();
    }


}
