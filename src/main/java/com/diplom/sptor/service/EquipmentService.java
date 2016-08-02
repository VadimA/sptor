package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.domain.StatusOfEquipment;
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
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment(){
        return this.equipmentRepository.findAll();
    }
    public Equipment addEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }
    public Equipment updateEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }
    public void deleteEquipment(int equipmentId){
        equipmentRepository.delete(equipmentId);
    }
    public Equipment getEquipmentById(int equipmentId){
        return this.equipmentRepository.findOne(equipmentId);
    }
    public List<Equipment> getEquipmentsBySubdivision(int subdivisionId){
        return equipmentRepository.findBySubdivision(subdivisionId);
    }
    public List<Equipment> getEquipmentsBySubdivisionAndType(int subdivisionId, int type){
        return equipmentRepository.findBySubdivisionAndTypeOfEquipment(subdivisionId, type);
    }
    public List<Equipment> getEquipmentsByStatus(StatusOfEquipment status){
        return equipmentRepository.findByStatus(status);
    }
}
