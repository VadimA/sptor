package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.WorkingHours;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface WorkingHoursRepository extends CrudRepository<WorkingHours, Integer> {

    List<WorkingHours> findAll();
    List<WorkingHours> findByEquipment(Equipment equipment);
}
