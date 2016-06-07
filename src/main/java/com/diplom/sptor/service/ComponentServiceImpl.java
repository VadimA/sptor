package com.diplom.sptor.service;

import com.diplom.sptor.dao.ComponentsDAO;
import com.diplom.sptor.dao.TechnologicalCardDAO;
import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 27.02.2016.
 */
@Component
public class ComponentServiceImpl implements ComponentService{

    private ComponentsDAO componentDao;

    public void setComponentDao(ComponentsDAO componentDao) {
        this.componentDao = componentDao;
    }

    @Transactional
    public void addComponent(Components components) {
        this.componentDao.addComponent(components);
    }

    @Transactional
    public List<Components> listComponents() {
        return this.componentDao.listComponents();
    }

    @Transactional
    public Components getComponentById(int id) {
        return this.componentDao.getComponentById(id);
    }

    @Transactional
    public void deleteComponent(int id) {
        this.componentDao.deleteComponent(id);
    }

    @Transactional
    public void updateComponent(Components components) {
        this.componentDao.updateComponent(components);
    }

    @Transactional
    public List<Components> getComponentsByType(TypeOfEquipment type){
        return this.componentDao.getComponentsByType(type);
    }
}
