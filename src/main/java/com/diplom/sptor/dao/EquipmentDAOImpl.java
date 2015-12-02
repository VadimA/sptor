package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 27.11.2015.
 */
@Repository
public class EquipmentDAOImpl implements EquipmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    public void addEquipment(Equipment equipment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(equipment);
    }

    @SuppressWarnings("unchecked")

    public List<Equipment> listEquipments() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Equipment> equipmentsList = session.createQuery("from Equipment ").list();

        return equipmentsList;
    }

    public Equipment getEquipmentById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.load(Equipment.class, new Integer(id));
        return equipment;
    }
}
