package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Subdivisions;
import com.diplom.sptor.service.EquipmentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 08.12.2015.
 */
public interface SubdivisionDAO {

    public void addSubdivision(Subdivisions subdivision);

    public List<Subdivisions> listSubdivisions();

    public Subdivisions getSubdivisionById(int id);

    public void deleteSubdivision(int id);

    public void updateSubdivision(Subdivisions subdivisions);
}
