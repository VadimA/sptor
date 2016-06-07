package com.diplom.sptor.dao;

import com.diplom.sptor.domain.DownTime;
import com.diplom.sptor.domain.Equipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by user on 14.04.2016.
 */
public class DownTimeDAOImpl implements DownTimeDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addDownTime(DownTime downTime) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(downTime);
    }

    public List<DownTime> listDownTime() {
        Session session = this.sessionFactory.getCurrentSession();
        List<DownTime> downTimes = session.createQuery("from com.diplom.sptor.domain.DownTime ").list();
        return  downTimes;
    }

    public List<DownTime> getDownTimeByEquipmentId(Equipment equipment) {
        Session session = this.sessionFactory.getCurrentSession();
        List<DownTime> downTimeList = session.createQuery("select work from com.diplom.sptor.domain.DownTime work " +
                "where work.equipment =:id order by work.date_of_adding").setParameter("id", equipment).list();
        return  downTimeList;
    }

    public void deleteDownTime(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        DownTime downTime = (DownTime) session.load(DownTime.class, new Integer(id));
        if(null != downTime){
            session.delete(downTime);
        }
    }

    public void updateDownTime(DownTime downTime) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(downTime);
    }

}
