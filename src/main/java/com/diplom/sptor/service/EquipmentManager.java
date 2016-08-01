package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.repository.EquipmentRepository;
import com.diplom.sptor.repository.SpareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
@Service
@Transactional
public class EquipmentManager {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment(){
        return this.equipmentRepository.findAll();
    }

    public Equipment saveSpare(Equipment equipment){
        return equipmentRepository.save(equipment);
    }
    public Equipment getEquipmentByEquipmentId(int equipmentId){
        return equipmentRepository.findByEquipmentId(equipmentId);
    }
}
