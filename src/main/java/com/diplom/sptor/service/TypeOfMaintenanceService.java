package com.diplom.sptor.service;

import com.diplom.sptor.domain.TypeOfMaintenance;

import java.util.List;

/**
 * Created by user on 25.03.2016.
 */
public interface TypeOfMaintenanceService {

    public void addType(TypeOfMaintenance typeOfMaintenance);

    public List<TypeOfMaintenance> listType();

    public TypeOfMaintenance getTypeById(int id);

    public void deleteType(int id);

    public void updateType(TypeOfMaintenance typeOfMaintenance);
}
