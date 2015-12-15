package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.domain.Subdivisions;

import java.util.List;

/**
 * Created by user on 14.12.2015.
 */
public interface SpareDAO {

    public void addSpare(Spares spares);

    public List<Spares> listSpares();

    public Spares getSpareById(int id);

    public void deleteSpare(int id);

    public void updateSpare(Spares spares);

}
