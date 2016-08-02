package com.diplom.sptor.repository;

import com.diplom.sptor.domain.StatusOfEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface StatusOfEqRepository extends CrudRepository<StatusOfEquipment, Integer> {

    List<StatusOfEquipment> findAll();
}
