package com.diplom.sptor.service;

import com.diplom.sptor.dao.EquipmentDAO;
import com.diplom.sptor.dao.EquipmentDAOImpl;
import com.diplom.sptor.domain.Equipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 01.12.2015.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentDAO equipmentDAO;

    public void setEquipmentDAO(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    @Transactional
    public void addEquipment(Equipment equipment) {
        this.equipmentDAO.addEquipment(equipment);
    }

    @Transactional
    public List<Equipment> listEquipments() {
        return this.equipmentDAO.listEquipments();
    }

    @Transactional
    public Equipment getEquipmentById(int id) {
        return this.equipmentDAO.getEquipmentById(id);
    }
}
