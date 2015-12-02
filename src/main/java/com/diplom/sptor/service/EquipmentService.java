package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;

import java.util.List;

/**
 * Created by user on 01.12.2015.
 */
public interface EquipmentService {

    public void addEquipment(Equipment equipment);

    public List<Equipment> listEquipments();

    public Equipment getEquipmentById(int id);

}
