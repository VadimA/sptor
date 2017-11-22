package com.diplom.sptor.service;

import com.diplom.sptor.domain.TypeOfMainToEquipment;
import com.diplom.sptor.repository.TypeOfMainToEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by vanosov on 20.11.2017.
 */
@Service
@Transactional
public class TypeOfMainToEquipmentService {

    @Autowired
    TypeOfMainToEquipmentRepository typeOfMainToEquipmentRepository;

    public List<TypeOfMainToEquipment> findAll(){
        return typeOfMainToEquipmentRepository.findAll();
    }

    public List<TypeOfMainToEquipment> findByTypeOfEquipmentId(int typeOfEquipmentId){
        return typeOfMainToEquipmentRepository.findByTypeOfEquipmentId(typeOfEquipmentId);
    }

    public List<TypeOfMainToEquipment> findByTypeOfMaintenanceId(int typeOfMaintenanceId){
        return typeOfMainToEquipmentRepository.findByTypeOfMaintenanceId(typeOfMaintenanceId);
    }

    public List<TypeOfMainToEquipment> findByTypeOfEquipmentIdAndTypeOfMaintenanceId(int typeOfEquipmentId, int typeOfMaintenanceId){
        return typeOfMainToEquipmentRepository.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(typeOfEquipmentId, typeOfMaintenanceId);

    }

    public TypeOfMainToEquipment addType(TypeOfMainToEquipment typeOfMainToEquipment){
        return  typeOfMainToEquipmentRepository.save(typeOfMainToEquipment);
    }

    public void deleteType(int id){
        typeOfMainToEquipmentRepository.delete(id);
    }

    public TypeOfMainToEquipment updateType(TypeOfMainToEquipment typeOfMainToEquipment){
        return typeOfMainToEquipmentRepository.save(typeOfMainToEquipment);
    }

}
