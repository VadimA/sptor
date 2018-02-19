package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Graphic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 18.02.2018.
 */
public interface GraphicRepository extends CrudRepository<Graphic, Integer> {

    List<Graphic> findAll();
    List<Graphic> findByGraphicId(int graphicId);
}