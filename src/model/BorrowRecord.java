package model;

import java.time.LocalDate;
import java.util.UUID;
import model.base.Record;

public class BorrowRecord extends Record {
    private LocalDate borrow_date;
    private UUID document_id;

    public BorrowRecord() {
    }

    public BorrowRecord(UUID id, UUID user_id, UUID document_id, LocalDate borrow_date) {
        super(id, user_id);
        this.document_id = document_id;
        this.borrow_date = borrow_date;
    }

    public UUID getDocument_id() {
        return document_id;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setDocument_id(UUID document_id) {
        this.document_id = document_id;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }
}
