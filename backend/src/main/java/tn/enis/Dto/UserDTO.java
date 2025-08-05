package tn.enis.Dto;

public class UserDTO {
    private Long id;
    private String fullName;
    private String password;
    private String role; // ✅ Ajouter cet attribut

    public UserDTO() {}

    public UserDTO(Long id, String fullName, String password, String role) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
