package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 14.12.2015.
 */
@Repository
public class SpareDAOImpl implements SpareDAO{

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addSpare(Spares spares) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(spares);
    }

    public List<Spares> listSpares() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Spares> sparesList = session.createQuery("from com.diplom.sptor.domain.Spares ").list();
        return  sparesList;
    }

    public Spares getSpareById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Spares spares = (Spares) session.load(Spares.class, new Integer(id));
        return spares;
    }

    public void deleteSpare(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Spares spares = (Spares) session.load(Spares.class, new Integer(id));
        if(null != spares){
            session.delete(spares);
        }
    }

    public void updateSpare(Spares spares) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(spares);
    }
}
