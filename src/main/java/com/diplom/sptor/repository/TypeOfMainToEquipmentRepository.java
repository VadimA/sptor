package com.diplom.sptor.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 16.11.2017.
 */
public interface TypeOfMainToEquipmentRepository extends CrudRepository<TypeOfMainToEquipmentRepository, Integer> {

    List<TypeOfMainToEquipmentRepository> findAll();

    List<TypeOfMainToEquipmentRepository> findByTypeOfEquipmentId(int typeOfEquipmentId);

    List<TypeOfMainToEquipmentRepository> findByTypeOfMaintenanceId(int typeOfMaintenanceId);

    List<TypeOfMainToEquipmentRepository> findByTypeOfEquipmentIdAndTypeOfMaintenanceId(int typeOfEquipmentId, int typeOfMaintenanceId);

}