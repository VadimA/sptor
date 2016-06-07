package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.StatusOfEquipment;
import com.diplom.sptor.domain.Subdivisions;

import java.util.List;
import java.util.Set;

/**
 * Created by user on 27.11.2015.
 */
public interface EquipmentDAO {

    public Equipment addEquipment(Equipment equipment);

    public List<Equipment> listEquipments();

    public Equipment getEquipmentById(int id);

    public void deleteEquipment(int id);

    public void updateEquipment(Equipment equipment);

    public Set<Equipment> getEquipmentsBySubdivision(int subdivision_id);

    public Set<Equipment> getEquipmentsBySubdivisionAndType(int subdivision_id, int subdivision_type);

    public List<Equipment> getEquipmentsByStatus(int status);
}
