package model;

import java.time.LocalDate;
import java.util.UUID;

public class ReturnRecord {
    private UUID id;
    private UUID borrow_id;
    private LocalDate return_date;

    public ReturnRecord() {}

    public ReturnRecord(UUID id, UUID borrow_id, LocalDate return_date) {
        this.id = id; this.borrow_id = borrow_id; this.return_date = return_date;
    }

    public UUID getId() { return id; }
    public UUID getBorrow_id() { return borrow_id; }
    public LocalDate getReturn_date() { return return_date; }

    public void setId(UUID id) { this.id = id; }
    public void setBorrow_id(UUID borrow_id) { this.borrow_id = borrow_id; }
    public void setReturn_date(LocalDate return_date) { this.return_date = return_date; }
}
