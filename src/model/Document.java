

package model;

public class Document {
    private int id;
    private String title;
    private String author;

    public Document() {}

    public Document(int id, String title, String author) {
        this.id     = id;
        this.title  = title;
        this.author = author;
    }

    // getters / setters
    public int getId()             { return id; }
    public void setId(int id)      { this.id = id; }
    public String getTitle()       { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getAuthor()      { return author; }
    public void setAuthor(String a){ this.author = a; }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " — " + author;
    }
}

