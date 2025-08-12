package controller;

import model.Document;
import service.DocumentService;

import java.util.List;
import java.util.UUID;

public class DocumentController {
    private final DocumentService documentService;

    public DocumentController() {
        this.documentService = new DocumentService();
    }

    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    public boolean addDocument(Document doc) {
        return documentService.addDocument(doc);
    }

    public boolean updateDocument(Document doc) {
        return documentService.updateDocument(doc);
    }

    public boolean deleteDocument(UUID id) {
        return documentService.deleteDocument(id);
    }
}