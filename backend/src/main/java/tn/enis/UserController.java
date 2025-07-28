package tn.enis;

import tn.enis.jwt.JwtUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

            // ✅ Retourne aussi userId ici
            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "userId", user.getId()
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

}
