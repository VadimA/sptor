package com.diplom.sptor.service;

import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.repository.TypeOfMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 25.03.2016.
 */
@Service
@Transactional
public class TypeOfMaintenanceService {

    @Autowired
    TypeOfMaintenanceRepository typeOfMaintenanceRepository;

    public TypeOfMaintenance addType(TypeOfMaintenance typeOfMaintenance){
        return  typeOfMaintenanceRepository.save(typeOfMaintenance);
    }

    public List<TypeOfMaintenance> getAllTypes(){
        return typeOfMaintenanceRepository.findAll();
    }

    public TypeOfMaintenance getTypeById(int id){
        return typeOfMaintenanceRepository.findOne(id);
    }

    public void deleteType(int id){
        typeOfMaintenanceRepository.delete(id);
    }

    public TypeOfMaintenance updateType(TypeOfMaintenance typeOfMaintenance){
        return typeOfMaintenanceRepository.save(typeOfMaintenance);
    }
}
