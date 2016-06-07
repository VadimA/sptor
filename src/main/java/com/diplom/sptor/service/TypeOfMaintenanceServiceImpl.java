package com.diplom.sptor.service;

import com.diplom.sptor.dao.TypeOfMaintenanceDAO;
import com.diplom.sptor.domain.TypeOfMaintenance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 25.03.2016.
 */
@Service
public class TypeOfMaintenanceServiceImpl implements TypeOfMaintenanceService{

    private TypeOfMaintenanceDAO typeOfMaintenanceDAO;

    public void setTypeOfMaintenanceDAO(TypeOfMaintenanceDAO typeOfMaintenanceDAO) {
        this.typeOfMaintenanceDAO = typeOfMaintenanceDAO;
    }

    @Transactional
    public void addType(TypeOfMaintenance typeOfEquipment) {
        this.typeOfMaintenanceDAO.addType(typeOfEquipment);
    }

    @Transactional
    public void updateType(TypeOfMaintenance typeOfEquipment) {
        this.typeOfMaintenanceDAO.updateType(typeOfEquipment);
    }

    @Transactional
    public List<TypeOfMaintenance> listType() {
        return this.typeOfMaintenanceDAO.listType();
    }

    @Transactional
    public TypeOfMaintenance getTypeById(int id) {
        return this.typeOfMaintenanceDAO.getTypeById(id);
    }

    @Transactional
    public void deleteType(int id) {
        this.typeOfMaintenanceDAO.deleteType(id);
    }
}
