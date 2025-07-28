package tn.enis;

public class UserDTO {
    private Long id;
    private String fullName;
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String fullName, String password) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
