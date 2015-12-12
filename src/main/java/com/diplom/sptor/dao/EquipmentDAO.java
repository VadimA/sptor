package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;

import java.util.List;

/**
 * Created by user on 27.11.2015.
 */
public interface EquipmentDAO {

    public void addEquipment(Equipment equipment);

    public List<Equipment> listEquipments();

    public Equipment getEquipmentById(int id);

    public void deleteEquipment(int id);
}
