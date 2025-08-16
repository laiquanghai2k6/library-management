package controller;

import model.ReturnRecord;
import service.ReturnService;

import java.util.List;
import java.util.UUID;

public class ReturnController {
    private final ReturnService returnRecordService;

    public ReturnController() {
        this.returnRecordService = new ReturnService();
    }

    public List<ReturnRecord> getAllReturnRecords() {
        return returnRecordService.getAllReturns();
    }

    public boolean addReturnRecord(ReturnRecord record) {
        return returnRecordService.returnBook(record);
    }
    public boolean deleteReturnRecord(int id) {
        return returnRecordService.deleteReturn(id);
    }


}
