package com.diplom.sptor.service;

import com.diplom.sptor.domain.TechnologicalCard;

import java.util.List;

/**
 * Created by user on 15.12.2015.
 */
public interface TechnologicalCardService {
    public void addCard(TechnologicalCard technologicalCard);

    public List<TechnologicalCard> listCards();

    public TechnologicalCard getCardById(int id);

    public void deleteCard(int id);

    public void updateCard(TechnologicalCard technologicalCard);
}
