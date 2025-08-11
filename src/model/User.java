package model;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(UUID id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", email: " + email + "\n";
    }
}
