package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface ParametersRepository extends CrudRepository<Parameters, Integer> {

    List<Parameters> findAll();
    List<Parameters> findByTypeOfEquipment(TypeOfEquipment typeOfEquipment);
}
