package com.diplom.sptor.service;

import com.diplom.sptor.dao.ParametersDAO;
import com.diplom.sptor.dao.TypeOfEquipmentDAO;
import com.diplom.sptor.dao.WorkingHoursDAO;
import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import com.diplom.sptor.domain.WorkingHours;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 14.03.2016.
 */
@Component
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private WorkingHoursDAO workingHoursDAO;

    public void setWorkingHoursDAO(WorkingHoursDAO workingHoursDAO) {
        this.workingHoursDAO = workingHoursDAO;
    }

    @Transactional
    public void addWorkingHours(WorkingHours workingHours) {
        this.workingHoursDAO.addWorkingHours(workingHours);
    }

    @Transactional
    public List<WorkingHours> listWorkingHours() {
        return this.workingHoursDAO.listWorkingHours();
    }

    @Transactional
    public List<WorkingHours> getWorkingHoursByEquipmentId(Equipment equipment) {
        return this.workingHoursDAO.getWorkingHoursByEquipmentId(equipment);
    }

    @Transactional
    public void deleteWorkingHours(int id) {
        this.workingHoursDAO.deleteWorkingHours(id);
    }

    @Transactional
    public void updateWorkingHours(WorkingHours workingHours) {
        this.workingHoursDAO.updateWorkingHours(workingHours);
    }
}
