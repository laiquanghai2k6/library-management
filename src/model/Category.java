package model;

public class Category {
    private int id;
    private String name;

    public Category() {}
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter
    public int getId() { return id; }
    public String getName() { return name; }

    // Setter
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
