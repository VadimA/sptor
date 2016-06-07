package com.diplom.sptor.dao;

import com.diplom.sptor.domain.TypeOfMaintenance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 25.03.2016.
 */
@Repository
public class TypeOfMaintenanceDAOImpl  implements TypeOfMaintenanceDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addType(TypeOfMaintenance typeOfMaintenance) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(typeOfMaintenance);
    }

    public List<TypeOfMaintenance> listType() {
        Session session = this.sessionFactory.getCurrentSession();
        List<TypeOfMaintenance> typeList = session.createQuery("from com.diplom.sptor.domain.TypeOfMaintenance ").list();
        return  typeList;
    }

    public TypeOfMaintenance getTypeById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TypeOfMaintenance typeOfEquipment = (TypeOfMaintenance) session.load(TypeOfMaintenance.class, new Integer(id));
        return typeOfEquipment;
    }

    public void deleteType(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TypeOfMaintenance typeOfEquipment = (TypeOfMaintenance) session.load(TypeOfMaintenance.class, new Integer(id));
        if(null != typeOfEquipment){
            session.delete(typeOfEquipment);
        }
    }

    public void updateType(TypeOfMaintenance typeOfMaintenance) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(typeOfMaintenance);
    }
}
