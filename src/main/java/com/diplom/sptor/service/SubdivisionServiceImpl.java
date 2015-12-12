package com.diplom.sptor.service;

import com.diplom.sptor.dao.EquipmentDAO;
import com.diplom.sptor.dao.SubdivisionDAO;
import com.diplom.sptor.domain.Subdivisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 08.12.2015.
 */
@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    private SubdivisionDAO subdivisionDAO;

    public void setSubdivisionDAO(SubdivisionDAO subdivisionDAO) {
        this.subdivisionDAO = subdivisionDAO;
    }

    @Transactional
    public void addSubdivision(Subdivisions subdivision) {
        this.subdivisionDAO.addSubdivision(subdivision);
    }

    @Transactional
    public void updateSubdivision(Subdivisions subdivisions) {
        this.subdivisionDAO.updateSubdivision(subdivisions);
    }

    @Transactional
    public List<Subdivisions> listSubdivisions() {
        return this.subdivisionDAO.listSubdivisions();
    }

    @Transactional
         public Subdivisions getSubdivisionById(int id) {
        return this.subdivisionDAO.getSubdivisionById(id);
    }

    @Transactional
    public void deleteSubdivision(int id) {
        this.subdivisionDAO.deleteSubdivision(id);
    }
}
