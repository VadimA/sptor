package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Subdivisions;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 13.12.2015.
 */
@Repository
public class TypeOfEquipmentDAOImpl implements TypeOfEquipmentDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addType(TypeOfEquipment typeOfEquipment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(typeOfEquipment);
    }

    public List<TypeOfEquipment> listType() {
        Session session = this.sessionFactory.getCurrentSession();
        List<TypeOfEquipment> typeList = session.createQuery("from com.diplom.sptor.domain.TypeOfEquipment ").list();
        return  typeList;
    }

    public TypeOfEquipment getTypeById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TypeOfEquipment typeOfEquipment = (TypeOfEquipment) session.load(TypeOfEquipment.class, new Integer(id));
        return typeOfEquipment;
    }

    public void deleteType(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TypeOfEquipment typeOfEquipment = (TypeOfEquipment) session.load(TypeOfEquipment.class, new Integer(id));
        if(null != typeOfEquipment){
            session.delete(typeOfEquipment);
        }
    }

    public void updateType(TypeOfEquipment typeOfEquipment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(typeOfEquipment);
    }

}
