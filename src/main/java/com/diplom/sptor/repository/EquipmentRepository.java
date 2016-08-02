package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Equipment;
import java.util.List;

import com.diplom.sptor.domain.StatusOfEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {

    List<Equipment> findAll();
    List<Equipment> findBySubdivision(int subdivision);
    List<Equipment> findBySubdivisionAndTypeOfEquipment(int subdivision, int type);
    List<Equipment> findByStatus(StatusOfEquipment status);
}
