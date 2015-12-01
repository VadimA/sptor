package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 27.11.2015.
 */
public class EquipmentDAOImpl implements EquipmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void addEquipment(Equipment equipment) {
        sessionFactory.getCurrentSession().save(equipment);
    }

    @SuppressWarnings("unchecked")
    public List<Equipment> getEquipments() {
        return sessionFactory.getCurrentSession().createQuery("Select equipment_name, equipment_id from equipment").list();
    }

    public void removeEquipments(Integer id) {
        Equipment equipment = (Equipment)sessionFactory.getCurrentSession().load(Equipment.class, id);

        if(null != equipment){
            sessionFactory.getCurrentSession().delete(equipment);
        }
    }
}
