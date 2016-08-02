package com.diplom.sptor.service;

import com.diplom.sptor.domain.Document;
import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentsRepository documentsRepository;

    public void addDocument(Document document) {
        documentsRepository.save(document);
    }
    public List<Document> getAllDocument() {
         return documentsRepository.findAll();
    }

    public List<Document> getDocumentsByEquipment(Equipment equipment){
        return documentsRepository.findByEquipment(equipment);
    }

    public Document getDocument(Integer document_id) {
       return documentsRepository.findOne(document_id);
    }

    public void deleteDocument(int document_id) {
        documentsRepository.delete(document_id);
    }
}
