package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Status;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.repository.TechnologicalCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 15.12.2015.
 */
@Service
@Transactional
public class TechnologicalCardService {

    @Autowired
    TechnologicalCardRepository technologicalCardRepository;

    public TechnologicalCard addCard(TechnologicalCard technologicalCard){
        return technologicalCardRepository.save(technologicalCard);
    }

    public List<TechnologicalCard> getAllCards(){
        return technologicalCardRepository.findAll();
    }

    public TechnologicalCard getCardById(int id){
        return technologicalCardRepository.findOne(id);
    }

    public void deleteCard(int id){
        technologicalCardRepository.delete(id);
    }

    public TechnologicalCard updateCard(TechnologicalCard technologicalCard){
        return technologicalCardRepository.save(technologicalCard);
    }

    public List<TechnologicalCard> getTechCardByEquipment(Equipment equipment){
        return technologicalCardRepository.findByEquipment(equipment);
    }

    public List<TechnologicalCard> findByEquipmentAndTypeOfMaintenance(Equipment equipment, TypeOfMaintenance typeOfMaintenance){
        return technologicalCardRepository.findByEquipmentAndTypeOfMaintenance(equipment, typeOfMaintenance);
    }

    public List<TechnologicalCard> getTechnologicalCardByStatus(Status status){
        return technologicalCardRepository.findByStatus(status);
    }
}
