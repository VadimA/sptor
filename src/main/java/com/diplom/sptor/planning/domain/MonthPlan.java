package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonthPlan implements GeneralPlan{

    public Graphic generatePlan(){
        return new Graphic();
                }

public List<RepairUnit> getListOfEquipmentsWhereNeedsInMaintenance(List<Equipment> equipmentList, List<TypeOfMaintenance> typeOfMaintenanceList){
        return null;
        }
        }
