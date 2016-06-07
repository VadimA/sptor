package com.diplom.sptor.service;

import com.diplom.sptor.domain.TypeOfEquipment;

import java.util.List;

/**
 * Created by user on 13.12.2015.
 */
public interface TypeOfEquipmentService {

    public void addType(TypeOfEquipment typeOfEquipment);

    public List<TypeOfEquipment> listType();

    public TypeOfEquipment getTypeById(int id);

    public void deleteType(int id);

    public void updateType(TypeOfEquipment typeOfEquipment);

}
