package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 27.02.2016.
 */
@Repository
public class ComponentsDaoImpl implements ComponentsDAO{

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addComponent(Components components) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(components);
    }

    public List<Components> listComponents() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Components> cardList = session.createQuery("from com.diplom.sptor.domain.Components ").list();
        return  cardList;
    }

    public Components getComponentById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Components components  = (Components) session.load(Components.class, new Integer(id));
        return components;
    }

    public void deleteComponent(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Components components = (Components) session.load(Components.class, new Integer(id));
        if(null != components){
            session.delete(components);
        }
    }

    public void updateComponent(Components components) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(components);
    }

    public List<Components> getComponentsByType(TypeOfEquipment type){
        Session session = this.sessionFactory.getCurrentSession();
        List<Components> cardList = session.createQuery(" select comp from com.diplom.sptor.domain.Components comp where comp.type_of_equipment_components =:id ").setParameter("id", type).list();
        return  cardList;
    }
}