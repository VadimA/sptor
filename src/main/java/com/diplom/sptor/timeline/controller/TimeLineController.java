package com.diplom.sptor.timeline.controller;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.repository.TechnologicalCardRepository;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.TechnologicalCardService;
import com.diplom.sptor.timeline.domain.Event;
import com.diplom.sptor.timeline.domain.TimeLine;
import com.diplom.sptor.timeline.util.TimeLineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanosov on 02.02.2018.
 */
@RestController
@RequestMapping("/timeline")
public class TimeLineController {

    @Autowired
    TechnologicalCardService technologicalCardService;

    @Autowired
    EquipmentService equipmentService;

    @RequestMapping(value = "/equipments/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody TimeLine prepareTimeLineEntity(@PathVariable(value = "equipmentId") int equipmentId){
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        List<TechnologicalCard> technologicalCardList = technologicalCardService.getTechCardByEquipment(equipment);
        TimeLine timeLine = new TimeLine();
        List<Event> events = new ArrayList<Event>();
        for(TechnologicalCard techCard: technologicalCardList){
            events.add(TimeLineUtils.mapTechCardToEvent(techCard));
        }
        timeLine.setEvents(events);
        return timeLine;
    }
}
