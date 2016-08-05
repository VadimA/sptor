package com.diplom.sptor.service;

import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.TypeOfEquipment;
import com.diplom.sptor.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 01.08.2016.
 */
@Service
@Transactional
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public List<Components> getAllComponents(){
        return this.componentRepository.findAll();
    }

    public Components addComponent(Components components){
        return componentRepository.save(components);
    }

    public Components getComponentById(int id){
        return componentRepository.findByComponentId(id);
    }

    public List<Components> getComponentByType(TypeOfEquipment typeOfEquipment){
        return componentRepository.findByTypeOfEquipment(typeOfEquipment);
    }
}
