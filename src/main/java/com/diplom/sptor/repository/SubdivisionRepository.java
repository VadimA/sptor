package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Subdivisions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface SubdivisionRepository extends CrudRepository<Subdivisions, Integer> {

    List<Subdivisions> findAll();
}
