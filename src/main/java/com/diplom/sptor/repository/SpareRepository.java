package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Spares;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
public interface SpareRepository extends CrudRepository<Spares, Integer> {
    List<Spares> findAll();
    Spares save(Spares spare);
    Spares findSparesById(int spare_id);
    void delete(Spares spares);
}
