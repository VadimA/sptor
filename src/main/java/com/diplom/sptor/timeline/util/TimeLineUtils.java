package com.diplom.sptor.timeline.util;

import com.diplom.sptor.domain.RepairSheet;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.timeline.domain.*;

import java.util.Date;

public class TimeLineUtils {

    public static Event mapTechCardToEvent(TechnologicalCard technologicalCard){
        Event event = new Event();
        event.setBackground(fillBackGround());
        event.setStartDate(fillStartDate(technologicalCard.getStart_date()));
        event.setEndDate(fillEndDate(technologicalCard.getEnd_date()));
        event.setText(fillTextToTechCard(technologicalCard));
        return event;
    }

    public static Event mapRepairSheetToEvent(RepairSheet repairSheet){
        Event event = new Event();
        event.setBackground(fillBackGround());
        event.setStartDate(fillStartDate(repairSheet.getStart_date()));
        event.setEndDate(fillEndDate(repairSheet.getEnd_date()));
        event.setText(fillTextToRepairSheet(repairSheet));
        return event;
    }

    public static Background fillBackGround(){
        Background background = new Background();
        background.setColor("#999999");
        background.setOpacity(50);
        return background;
    }

    public static StartDate fillStartDate(Date date){
        StartDate startDate = new StartDate();
        startDate.setYear(String.valueOf(date.getYear()));
        startDate.setMonth(String.valueOf(date.getMonth()));
        startDate.setDay((String.valueOf(date.getDay())));
        return startDate;
    }

    public static EndDate fillEndDate(Date date){
        EndDate endDate = new EndDate();
        endDate.setYear(String.valueOf(date.getYear()));
        endDate.setMonth(String.valueOf(date.getMonth()));
        endDate.setDay((String.valueOf(date.getDay())));
        return endDate;
    }

    public static Text fillTextToTechCard(TechnologicalCard technologicalCard){
        Text text = new Text();
        text.setHeadline(technologicalCard.getType_of_maintenance().getType_of_maintenance_name() + " для " +
                        technologicalCard.getEquipment().getEquipmentName());
        text.setText("Номер карты: " + technologicalCard.getTechnological_card_number()
                + System.lineSeparator() + " Оборудование: " + technologicalCard.getEquipment().getEquipmentName()
                + System.lineSeparator() + " Тип ТО: " + technologicalCard.getType_of_maintenance().getType_of_maintenance_name()
                + System.lineSeparator() + " Дата: " + technologicalCard.getEnd_date().getDate()
                + System.lineSeparator() + " Ответственный: " + technologicalCard.getResponsible_for_delivery().getResponsible()
                + System.lineSeparator() + " Описание: " + technologicalCard.getDescription());
        return text;
    }

    public static Text fillTextToRepairSheet(RepairSheet repairSheet){
        Text text = new Text();
        text.setHeadline(repairSheet.getType_of_maintenance().getType_of_maintenance_name() + " для " +
                repairSheet.getEquipment().getEquipmentName());
        text.setText("Номер Листа: " + repairSheet.getSheet_number()
                + System.lineSeparator() + " Оборудование: " + repairSheet.getEquipment().getEquipmentName()
                + System.lineSeparator() + " Тип ремонта: " + repairSheet.getType_of_maintenance().getType_of_maintenance_name()
                + System.lineSeparator() + " Дата: " + repairSheet.getEnd_date().getDate()
                + System.lineSeparator() + " Ответственный: " + repairSheet.getResponsibleForDelivery().getResponsible()
                + System.lineSeparator() + " Описание: " + repairSheet.getDescription()
                + System.lineSeparator() + " Статус: " + repairSheet.getStatus().getStatus());
        return text;
    }

}
