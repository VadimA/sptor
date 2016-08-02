package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Document;
import com.diplom.sptor.domain.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface DocumentsRepository extends CrudRepository<Document, Integer> {

    List<Document> findAll();
    List<Document> findByEquipment(Equipment equipment);
}
