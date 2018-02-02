package com.diplom.sptor.timeline.util;

import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.timeline.domain.*;

import java.util.Date;

public class TimeLineUtils {

    public static Event mapTechCardToEvent(TechnologicalCard technologicalCard){
        Event event = new Event();
        event.setBackground(fillBackGround());
        event.setStartDate(fillStartDate(technologicalCard.getStart_date()));
        event.setEndDate(fillEndDate(technologicalCard.getEnd_date()));
        event.setText(fillText(technologicalCard));
        return null;
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

    public static Text fillText(TechnologicalCard technologicalCard){
        Text text = new Text();
        text.setHeadline(technologicalCard.getType_of_maintenance().getType_of_maintenance_name() + " для " +
                        technologicalCard.getEquipment().getEquipmentName());
        text.setText("Номер карты: " + technologicalCard.getTechnological_card_number()
                + "Оборудование: " + technologicalCard.getEquipment().getEquipmentName()
                + "Тип ремонта: " + technologicalCard.getType_of_maintenance().getType_of_maintenance_name()
                + "Дата: " + technologicalCard.getEnd_date().getDate()
                + "Ответственный: " + technologicalCard.getResponsible_for_delivery().getResponsible()
                + "Описание " + technologicalCard.getDescription());
        return text;
    }

}
