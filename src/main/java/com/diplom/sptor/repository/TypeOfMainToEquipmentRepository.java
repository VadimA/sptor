package com.diplom.sptor.repository;

import com.diplom.sptor.domain.TypeOfMainToEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 16.11.2017.
 */
public interface TypeOfMainToEquipmentRepository extends CrudRepository<TypeOfMainToEquipment, Integer> {

    List<TypeOfMainToEquipment> findAll();

    List<TypeOfMainToEquipment> findByTypeOfEquipmentId(int typeOfEquipmentId);

    List<TypeOfMainToEquipment> findByTypeOfMaintenanceId(int typeOfMaintenanceId);

    List<TypeOfMainToEquipment> findByTypeOfEquipmentIdAndTypeOfMaintenanceId(int typeOfEquipmentId, int typeOfMaintenanceId);

}