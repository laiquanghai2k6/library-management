package model;

import java.time.LocalDate;
import java.util.UUID;

public class BorrowRecord {
    private UUID id;
    private UUID user_id;
    private UUID document_id;
    private LocalDate borrow_date;

    public BorrowRecord() {}

    public BorrowRecord(UUID id, UUID user_id, UUID document_id, LocalDate borrow_date) {
        this.id = id; this.user_id = user_id; this.document_id = document_id; this.borrow_date = borrow_date;
    }

    public UUID getId() { return id; }
    public UUID getUser_id() { return user_id; }
    public UUID getDocument_id() { return document_id; }
    public LocalDate getBorrow_date() { return borrow_date; }

    public void setId(UUID id) { this.id = id; }
    public void setUser_id(UUID user_id) { this.user_id = user_id; }
    public void setDocument_id(UUID document_id) { this.document_id = document_id; }
    public void setBorrow_date(LocalDate borrow_date) { this.borrow_date = borrow_date; }
}
