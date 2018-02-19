package com.diplom.sptor.service;

import com.diplom.sptor.domain.Graphic;
import com.diplom.sptor.repository.GraphicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GraphicService {

    @Autowired
    GraphicRepository graphicRepository;

    public void addGraphic(Graphic graphic){
        graphicRepository.save(graphic);
    }

    public List<Graphic> getAllGraphics(){
        return graphicRepository.findAll();
    }

    public void deleteGraphic(int id){
        graphicRepository.delete(id);
    }

    public List<Graphic> getGraphicByGraphicId(int graphicId){
        return graphicRepository.findByGraphicId(graphicId);
    }

    public Graphic update(Graphic graphic){
        return graphicRepository.save(graphic);
    }
}
