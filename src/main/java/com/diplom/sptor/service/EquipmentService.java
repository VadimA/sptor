package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;

import java.util.List;

/**
 * Created by user on 27.11.2015.
 */
public interface EquipmentService {

    public void addEquipment(Equipment equipment);

    public List<Equipment> listEquipments();

    public void removeEquipment(Integer id);
}
