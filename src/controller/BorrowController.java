package controller;

import model.BorrowRecord;
import service.BorrowService;

import java.util.List;
import java.util.UUID;

public class BorrowController {
    private final BorrowService borrowRecordService;
    

    public BorrowController() {
        this.borrowRecordService = new BorrowService();
    }

    public BorrowRecord getBorrowRecord(UUID documentId, UUID userId){
        return borrowRecordService.getBorrowRecord(documentId, userId);
    }
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrows();
    }

    public boolean addBorrowRecord(BorrowRecord record) {
        return borrowRecordService.borrowBook(record);
    }

    public boolean deleteBorrowRecord(UUID id) {
        return borrowRecordService.deleteBorrow(id);
    }
}
