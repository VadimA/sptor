package com.diplom.sptor.repository;

import com.diplom.sptor.domain.DownTime;
import com.diplom.sptor.domain.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface DownTimeRepository extends CrudRepository<DownTime, Integer> {

    List<DownTime> findAll();
    List<DownTime> findByEquipment(Equipment equipment);
}
