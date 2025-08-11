package model;

import java.time.LocalDate;
import java.util.UUID;

public class Rating {
    private UUID id;
    private UUID document_id;
    private UUID user_id;
    private String comment;
    private LocalDate created_at;


    public Rating() {}
    public Rating(UUID id, UUID document_id,UUID user_id, LocalDate created_at, String comment) {
        this.id = id;
        this.document_id = document_id;
        this.user_id = user_id;
        this.comment = comment;
        this.created_at = created_at;
    }

    public UUID getId() { return id; }
    public UUID getDocument_id() { return document_id; }
    public UUID getUser_id() { return user_id; }
    public String getComment() { return comment; }
    public LocalDate getCreatedAt() { return created_at; }


    public void setId(UUID id) { this.id = id; }
    public void setDocument_id(UUID document_id) { this.document_id = document_id; }
    public void setUser_id(UUID user_id) { this.user_id = user_id; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreated_at(LocalDate created_at) { this.created_at = created_at; }


}
