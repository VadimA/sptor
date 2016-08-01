package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Spares;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {

    List<Equipment> findAll();
    Equipment findByEquipmentId(int equipmentId);
}
