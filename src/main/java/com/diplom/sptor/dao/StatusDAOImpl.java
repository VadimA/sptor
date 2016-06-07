package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.domain.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 31.03.2016.
 */
@Repository
@Transactional
public class StatusDAOImpl implements StatusDAO{

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addStatus(Status status) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(status);
    }

    public List<Status> listStatus() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Status> statusList = session.createQuery("from com.diplom.sptor.domain.Status ").list();
        return  statusList;
    }

    public Status getStatusById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Status status = (Status) session.load(Status.class, new Integer(id));
        return status;
    }

    public void deleteStatus(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Status status = (Status) session.load(Status.class, new Integer(id));
        if(null != status){
            session.delete(status);
        }
    }

    public void updateStatus(Status status) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(status);
    }
}
