package com.diplom.sptor.service;

import com.diplom.sptor.dao.SpareDAO;
import com.diplom.sptor.dao.SubdivisionDAO;
import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.domain.Subdivisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 14.12.2015.
 */
@Service
public class SpareServiceImpl implements SpareService {

    private SpareDAO spareDAO;

    public void setSpareDAO(SpareDAO spareDAO) {
        this.spareDAO = spareDAO;
    }

    @Transactional
    public void addSpare(Spares spares) {
        this.spareDAO.addSpare(spares);
    }

    @Transactional
    public void updateSpare(Spares spares) {
        this.spareDAO.updateSpare(spares);
    }

    @Transactional
    public List<Spares> listSpares() {
        return this.spareDAO.listSpares();
    }

    @Transactional
    public Spares getSpareById(int id) {
        return this.spareDAO.getSpareById(id);
    }

    @Transactional
    public void deleteSpare(int id) {
        this.spareDAO.deleteSpare(id);
    }
}
