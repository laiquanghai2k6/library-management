package model;

import java.time.LocalDate;

public class BorrowRecord {
    private int id;
    private int user_id;
    private int document_id;
    private LocalDate borrow_date;

    public BorrowRecord() {}

    public BorrowRecord(int id, int user_id, int document_id, LocalDate borrow_date) {
        this.id = id; this.user_id = user_id; this.document_id = document_id; this.borrow_date = borrow_date;
    }

    public int getId() { return id; }
    public int getUser_id() { return user_id; }
    public int getDocument_id() { return document_id; }
    public LocalDate getBorrow_date() { return borrow_date; }

    public void setId(int id) { this.id = id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setDocument_id(int document_id) { this.document_id = document_id; }
    public void setBorrow_date(LocalDate borrow_date) { this.borrow_date = borrow_date; }
}
