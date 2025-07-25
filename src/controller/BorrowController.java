package controller;

import model.BorrowRecord;
import service.BorrowService;

import java.util.List;

public class BorrowController {
    private final BorrowService borrowRecordService;

    public BorrowController() {
        this.borrowRecordService = new BorrowService();
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrows();
    }

    public boolean addBorrowRecord(BorrowRecord record) {
        return borrowRecordService.borrowBook(record);
    }

    public boolean deleteBorrowRecord(int id) {
        return borrowRecordService.deleteBorrow(id);
    }
}
