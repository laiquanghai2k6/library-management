package model;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;

    public Category() {}
    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter
    public UUID getId() { return id; }
    public String getName() { return name; }

    // Setter
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
