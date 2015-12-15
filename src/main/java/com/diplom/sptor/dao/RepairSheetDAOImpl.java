package com.diplom.sptor.dao;

import com.diplom.sptor.domain.RepairSheet;
import com.diplom.sptor.domain.Spares;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
@Repository
public class RepairSheetDAOImpl implements RepairSheetDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addRepairSheet(RepairSheet repairSheet) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(repairSheet);
    }

    public List<RepairSheet> listRepairSheets() {
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairSheets = session.createQuery("from com.diplom.sptor.domain.RepairSheet ").list();
        return  repairSheets;
    }

    public RepairSheet getRepairSheetById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        RepairSheet repairSheet = (RepairSheet) session.load(RepairSheet.class, new Integer(id));
        return repairSheet;
    }

    public void deleteRepairSheet(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        RepairSheet repairSheet = (RepairSheet) session.load(RepairSheet.class, new Integer(id));
        if(null != repairSheet){
            session.delete(repairSheet);
        }
    }

    public void updateRepairSheet(RepairSheet repairSheet) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(repairSheet);
    }
}
