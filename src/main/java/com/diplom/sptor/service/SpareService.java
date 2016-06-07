package com.diplom.sptor.service;

import com.diplom.sptor.domain.Spares;

import java.util.List;

/**
 * Created by user on 14.12.2015.
 */
public interface SpareService {

    public void addSpare(Spares spares);

    public List<Spares> listSpares();

    public Spares getSpareById(int id);

    public void deleteSpare(int id);

    public void updateSpare(Spares spares);
}
