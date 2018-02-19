package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Graphic;
import com.diplom.sptor.domain.TypeOfMaintenance;

import java.util.List;

/**
 * Created by user on 01.12.2017.
 */
public interface GeneralPlan {

    Graphic generatePlan();

    List<RepairUnit> getListOfEquipmentsWhereNeedsInMaintenance(List<Equipment> equipmentList, List<TypeOfMaintenance> typeOfMaintenanceList);

}
