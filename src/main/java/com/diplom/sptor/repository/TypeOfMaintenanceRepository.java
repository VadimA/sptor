package com.diplom.sptor.repository;

import com.diplom.sptor.domain.TypeOfMaintenance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface TypeOfMaintenanceRepository extends CrudRepository<TypeOfMaintenance, Integer> {

    List<TypeOfMaintenance> findAll();
}
