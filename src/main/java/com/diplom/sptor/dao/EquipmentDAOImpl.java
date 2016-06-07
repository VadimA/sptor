package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.StatusOfEquipment;
import com.diplom.sptor.domain.Subdivisions;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by user on 27.11.2015.
 */
@Repository
@Transactional
public class EquipmentDAOImpl implements EquipmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    public Equipment addEquipment(Equipment equipment) {
        Equipment eq = equipment;
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(equipment);
        return eq;
    }

    @SuppressWarnings("unchecked")

    public List<Equipment> listEquipments() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Equipment> equipmentsList = session.createQuery("from com.diplom.sptor.domain.Equipment ").list();

        return equipmentsList;
    }

    public Equipment getEquipmentById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.load(Equipment.class, new Integer(id));
        return equipment;
    }

    public void deleteEquipment(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Equipment equipment = (Equipment) session.load(Equipment.class, new Integer(id));
        if(null != equipment){
            session.delete(equipment);
        }
    }

    public void updateEquipment(Equipment equipment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(equipment);
    }

    public Set<Equipment> getEquipmentsBySubdivision(int subdivision_id){
        Session session = this.sessionFactory.getCurrentSession();
        Subdivisions subdivisions= (Subdivisions) session.load(Subdivisions.class, new Integer(subdivision_id));//session.createQuery("from com.diplom.sptor.domain.Equipment eq where eq.subdivision_id=:subdivision_id").setParameter("subdivision_id",subdivision_id).list();
        Set<Equipment> equipmentsList = subdivisions.getEquipments_sub();
        return equipmentsList;
    }

    public Set<Equipment> getEquipmentsBySubdivisionAndType(int subdivision_id, int subdivision_type) {
        Session session = this.sessionFactory.getCurrentSession();
        Subdivisions subdivisions= (Subdivisions) session.createQuery("from com.diplom.sptor.domain.Equipment eq where eq.subdivision_id=:subdivision_id").setParameter("subdivision_id",subdivision_id).list();
        Set<Equipment> equipmentsList = subdivisions.getEquipments_sub();
        return equipmentsList;
    }

    public List<Equipment> getEquipmentsByStatus(int status) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Equipment> equipmentsList  = session.createQuery("from com.diplom.sptor.domain.Equipment eq where eq.status.status_id =:status").setParameter("status",status).list();
        return equipmentsList;
    }


}
