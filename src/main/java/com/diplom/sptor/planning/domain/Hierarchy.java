package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;

import java.util.List;
import java.util.Set;

public class Hierarchy {

    private String text;
    private List<Hierarchy> nodes;

    public Hierarchy() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Hierarchy> getNodes() {
        return nodes;
    }

    public void setNodes(List<Hierarchy> nodes) {
        this.nodes = nodes;
    }
}
