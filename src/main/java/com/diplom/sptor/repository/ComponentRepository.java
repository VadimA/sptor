package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 01.08.2016.
 */
public interface ComponentRepository extends CrudRepository<Components, Integer> {

    List<Components> findAll();
    Components save(Components component);
    void delete(Components component);
    Components findByComponentId(int componentId);
    List<Components> findByTypeOfEquipment(TypeOfEquipment typeOfEquipment);
}
