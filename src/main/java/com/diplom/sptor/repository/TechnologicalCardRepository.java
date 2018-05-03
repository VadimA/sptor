package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Status;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.domain.TypeOfMaintenance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
@Service
@Transactional
public interface TechnologicalCardRepository extends CrudRepository<TechnologicalCard, Integer> {

    List<TechnologicalCard> findAll();
    List<TechnologicalCard> findByEquipment(Equipment equipment);
    List<TechnologicalCard> findByEquipmentAndTypeOfMaintenance(Equipment equipment, TypeOfMaintenance typeOfMaintenance);
    List<TechnologicalCard> findByStatus(Status status);
}