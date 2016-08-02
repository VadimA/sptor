package com.diplom.sptor.service;

import com.diplom.sptor.domain.TypeOfEquipment;
import com.diplom.sptor.repository.TypeOfEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 13.12.2015.
 */
@Service
@Transactional
public class TypeOfEquipmentService {

    @Autowired
    TypeOfEquipmentRepository typeOfEquipmentRepository;

    public TypeOfEquipment addType(TypeOfEquipment typeOfEquipment){
        return typeOfEquipmentRepository.save(typeOfEquipment);
    }

    public List<TypeOfEquipment> getAllTypes(){
        return typeOfEquipmentRepository.findAll();
    }

    public TypeOfEquipment getTypeById(int id){
        return typeOfEquipmentRepository.findOne(id);
    }

    public void deleteType(int id){
        typeOfEquipmentRepository.delete(id);
    }

    public TypeOfEquipment updateType(TypeOfEquipment typeOfEquipment){
        return typeOfEquipmentRepository.save(typeOfEquipment);
    }

}
