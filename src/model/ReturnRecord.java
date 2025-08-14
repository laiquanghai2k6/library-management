package model;

import java.time.LocalDate;
import java.util.UUID;
import model.base.Record;

public class ReturnRecord extends Record {
    private LocalDate return_date;
    private UUID borrow_id;

    public ReturnRecord() {
    }

    public ReturnRecord(UUID id, UUID borrow_id, LocalDate return_date, UUID user_id) {
        super(id, user_id);
        this.borrow_id = borrow_id;
        this.return_date = return_date;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBorrow_id() {
        return borrow_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBorrow_id(UUID borrow_id) {
        this.borrow_id = borrow_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }
}
