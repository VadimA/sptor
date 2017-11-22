package com.diplom.sptor.web;

import com.diplom.sptor.service.TypeOfMainToEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by vanosov on 20.11.2017.
 */
@Controller
public class PlanningController {

    @Autowired
    TypeOfMainToEquipmentService typeOfMainToEquipmentService;

    public void setTypeOfMainToEquipmentService(TypeOfMainToEquipmentService typeOfMainToEquipmentService) {
        this.typeOfMainToEquipmentService = typeOfMainToEquipmentService;
    }


}
