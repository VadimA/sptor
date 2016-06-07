package com.diplom.sptor.service;

import com.diplom.sptor.dao.SubdivisionDAO;
import com.diplom.sptor.dao.TypeOfEquipmentDAO;
import com.diplom.sptor.domain.Subdivisions;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 13.12.2015.
 */
@Service
public class TypeOfEquipmentServiceImpl implements TypeOfEquipmentService{

    private TypeOfEquipmentDAO typeOfEquipmentDAO;

    public void setTypeOfEquipmentDAO(TypeOfEquipmentDAO typeOfEquipmentDAO) {
        this.typeOfEquipmentDAO = typeOfEquipmentDAO;
    }

    @Transactional
    public void addType(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipmentDAO.addType(typeOfEquipment);
    }

    @Transactional
    public void updateType(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipmentDAO.updateType(typeOfEquipment);
    }

    @Transactional
    public List<TypeOfEquipment> listType() {
        return this.typeOfEquipmentDAO.listType();
    }

    @Transactional
    public TypeOfEquipment getTypeById(int id) {
        return this.typeOfEquipmentDAO.getTypeById(id);
    }

    @Transactional
    public void deleteType(int id) {
        this.typeOfEquipmentDAO.deleteType(id);
    }

}
