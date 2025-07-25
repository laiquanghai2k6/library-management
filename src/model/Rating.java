package model;

public class Rating {
    private int id;
    private int document_id;
    private int score;
    private String comment;

    public Rating() {}
    public Rating(int id, int document_id, int score, String comment) {
        this.id = id;
        this.document_id = document_id;
        this.score = score;
        this.comment = comment;
    }

    public int getId() { return id; }
    public int getDocument_id() { return document_id; }
    public int getScore() { return score; }
    public String getComment() { return comment; }

    public void setId(int id) { this.id = id; }
    public void setDocument_id(int document_id) { this.document_id = document_id; }
    public void setScore(int score) { this.score = score; }
    public void setComment(String comment) { this.comment = comment; }
}
