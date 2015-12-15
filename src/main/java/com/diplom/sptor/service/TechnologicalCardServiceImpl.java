package com.diplom.sptor.service;

import com.diplom.sptor.dao.TechnologicalCardDAO;
import com.diplom.sptor.dao.TypeOfEquipmentDAO;
import com.diplom.sptor.domain.TechnologicalCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 15.12.2015.
 */
@Service
public class TechnologicalCardServiceImpl implements TechnologicalCardService {

    private TechnologicalCardDAO technologicalCardDAO;

    public void setTechnologicalCardDAO(TechnologicalCardDAO technologicalCardDAO) {
        this.technologicalCardDAO = technologicalCardDAO;
    }

    @Transactional
    public void addCard(TechnologicalCard technologicalCard) {
        this.technologicalCardDAO.addCard(technologicalCard);
    }

    @Transactional
    public List<TechnologicalCard> listCards() {
        return this.technologicalCardDAO.listCards();
    }

    @Transactional
    public TechnologicalCard getCardById(int id) {
        return this.technologicalCardDAO.getCardById(id);
    }

    @Transactional
    public void deleteCard(int id) {
        this.technologicalCardDAO.deleteCard(id);
    }

    @Transactional
    public void updateCard(TechnologicalCard technologicalCard) {
        this.technologicalCardDAO.updateCard(technologicalCard);
    }
}
