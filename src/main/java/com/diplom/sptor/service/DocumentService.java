package com.diplom.sptor.service;

import com.diplom.sptor.dao.DocumentDAO;
import com.diplom.sptor.domain.Document;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Component
public class DocumentService {


    private DocumentDAO documentDAO;

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public void saveDocument(Document document) {
        this.documentDAO.saveDocument(document);
    }

    public List<Document> listDocument() {
         return this.documentDAO.listDocument();
    }

    public Document getDocument(Integer document_id) {
       return this.documentDAO.getDocument(document_id);
    }

    public void removeDocument(Integer document_id) {
        this.documentDAO.removeDocument(document_id);
    }
}
