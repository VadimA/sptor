package com.diplom.sptor.service;

import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.TypeOfEquipment;

import java.util.List;

/**
 * Created by user on 27.02.2016.
 */
public interface ComponentService {

    public void addComponent(Components components);

    public List<Components> listComponents();

    public Components getComponentById(int id);

    public void deleteComponent(int id);

    public void updateComponent(Components components);

    public List<Components> getComponentsByType(TypeOfEquipment type);
}
