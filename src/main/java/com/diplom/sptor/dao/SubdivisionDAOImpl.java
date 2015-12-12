package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Subdivisions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 08.12.2015.
 */
@Repository
public class SubdivisionDAOImpl implements SubdivisionDAO {

    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addSubdivision(Subdivisions subdivision) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(subdivision);
    }
    public void updateSubdivision(Subdivisions subdivisions) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(subdivisions);
    }
    public List<Subdivisions> listSubdivisions() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Subdivisions> subdivisionList = session.createQuery("from Subdivisions").list();
        return  subdivisionList;
    }

    public Subdivisions getSubdivisionById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subdivisions subdivisions = (Subdivisions) session.load(Subdivisions.class, new Integer(id));
        return subdivisions;
    }

    public void deleteSubdivision(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subdivisions subdivisions = (Subdivisions) session.load(Subdivisions.class, new Integer(id));
        if(null != subdivisions){
            session.delete(subdivisions);
        }
    }
}
