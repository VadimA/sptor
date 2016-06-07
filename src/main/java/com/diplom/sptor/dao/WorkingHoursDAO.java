package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import com.diplom.sptor.domain.WorkingHours;

import java.util.List;

/**
 * Created by user on 14.03.2016.
 */
public interface WorkingHoursDAO {

    public void addWorkingHours(WorkingHours workingHours);

    public List<WorkingHours> listWorkingHours();

    public List<WorkingHours> getWorkingHoursByEquipmentId(Equipment equipment);

    public void deleteWorkingHours(int id);

    public void updateWorkingHours(WorkingHours workingHours);

}
