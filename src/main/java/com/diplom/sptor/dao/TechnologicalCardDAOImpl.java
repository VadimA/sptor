package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TechnologicalCard;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 15.12.2015.
 */
@Repository
public class TechnologicalCardDAOImpl implements TechnologicalCardDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addCard(TechnologicalCard technologicalCard) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(technologicalCard);
    }

    public List<TechnologicalCard> listCards() {
        Session session = this.sessionFactory.getCurrentSession();
        List<TechnologicalCard> cardList = session.createQuery("from com.diplom.sptor.domain.TechnologicalCard ").list();
        return  cardList;
    }

    public TechnologicalCard getCardById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TechnologicalCard technologicalCard = (TechnologicalCard) session.load(TechnologicalCard.class, new Integer(id));
        return technologicalCard;
    }

    public List<TechnologicalCard> getCardByEquipmentId(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        List<TechnologicalCard> cardList = session.createQuery(" select card from com.diplom.sptor.domain.TechnologicalCard card where card.equipment_id =:id ").setParameter("id", id).list();
        return cardList;
    }

    public void deleteCard(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TechnologicalCard technologicalCard = (TechnologicalCard) session.load(TechnologicalCard.class, new Integer(id));
        if(null != technologicalCard){
            session.delete(technologicalCard);
        }
    }

    public void updateCard(TechnologicalCard technologicalCard) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(technologicalCard);
    }

    public List<TechnologicalCard> getTechCardByEquipment(Equipment equipment) {
        Session session = this.sessionFactory.getCurrentSession();
        List<TechnologicalCard> technologicalCardList = session.createQuery(" select tech from com.diplom.sptor.domain.TechnologicalCard tech " +
                "where tech.equipment =:equipment ").setParameter("equipment", equipment).list();
        return technologicalCardList;
    }
}
