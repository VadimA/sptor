package com.diplom.sptor.service;

import com.diplom.sptor.dao.EquipmentDAO;
import com.diplom.sptor.dao.EquipmentDAOImpl;
import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.StatusOfEquipment;
import com.diplom.sptor.domain.Subdivisions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public Equipment addEquipment(Equipment equipment) {
        return this.equipmentDAO.addEquipment(equipment);
    }

    @Transactional
    public List<Equipment> listEquipments() {
        return this.equipmentDAO.listEquipments();
    }

    @Transactional
    public Equipment getEquipmentById(int id) {
        return this.equipmentDAO.getEquipmentById(id);
    }
    @Transactional
    public void deleteEquipment(int id) {
        this.equipmentDAO.deleteEquipment(id);
    }
    @Transactional
    public void updateEquipment(Equipment equipment) {
        this.equipmentDAO.updateEquipment(equipment);
    }

    @Transactional
    public Set<Equipment> getEquipmentsBySubdivision(int subdivision_id){
       return this.equipmentDAO.getEquipmentsBySubdivision(subdivision_id);
    }

    @Transactional
    public List<Equipment> getEquipmentsByStatus(int status){
        return  this.equipmentDAO.getEquipmentsByStatus(status);
    }
}
