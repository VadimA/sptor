package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Subdivisions;

import java.util.List;

/**
 * Created by user on 08.12.2015.
 */
public interface SubdivisionService {

    public void addSubdivision(Subdivisions subdivision);

    public List<Subdivisions> listSubdivisions();

    public Subdivisions getSubdivisionById(int id);

    public void deleteSubdivision(int id);

    public void updateSubdivision(Subdivisions subdivisions);

}
