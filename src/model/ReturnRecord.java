package model;

import java.time.LocalDate;

public class ReturnRecord {
    private int id;
    private int borrow_id;
    private LocalDate return_date;

    public ReturnRecord() {}

    public ReturnRecord(int id, int borrow_id, LocalDate return_date) {
        this.id = id; this.borrow_id = borrow_id; this.return_date = return_date;
    }

    public int getId() { return id; }
    public int getBorrow_id() { return borrow_id; }
    public LocalDate getReturn_date() { return return_date; }

    public void setId(int id) { this.id = id; }
    public void setBorrow_id(int borrow_id) { this.borrow_id = borrow_id; }
    public void setReturn_date(LocalDate return_date) { this.return_date = return_date; }
}
