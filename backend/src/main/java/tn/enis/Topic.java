package tn.enis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes;


    @Transient
    private Map<String, String> definitions = new HashMap<>();
    @Transient
    private String summary;

    @ManyToOne
    @JsonBackReference
    private User user;



    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Map<String, String> getDefinitions() { return definitions; }
    public void setDefinitions(Map<String, String> definitions) { this.definitions = definitions; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
