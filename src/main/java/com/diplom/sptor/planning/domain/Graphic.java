package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="toir.graphics")
@Proxy(lazy=false)
public class Graphic {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "graph_id")
    private int graphicId;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @Column(name = "created_by")
    private User createdBy;

    @Column(name = "date_of_modification")
    private Date dateOfModification;

    @Column(name = "modified_by")
    private User modifiedBy;

    private String status = GraphicStatus.CRAEATED.getGraphicStatus();

    @Column(name = "is_year")
    private boolean isYear;

    public Graphic() {
    }

    public Graphic(Date dateOfCreation, User createdBy, Date dateOfModification, User modifiedBy,
                   String status, boolean isYear) {
        this.dateOfCreation = dateOfCreation;
        this.createdBy = createdBy;
        this.dateOfModification = dateOfModification;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.isYear = isYear;
    }

    public int getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(int graphicId) {
        this.graphicId = graphicId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(Date dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isYear() {
        return isYear;
    }

    public void setYear(boolean year) {
        isYear = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graphic graphic = (Graphic) o;

        if (graphicId != graphic.graphicId) return false;
        if (isYear != graphic.isYear) return false;
        if (!dateOfCreation.equals(graphic.dateOfCreation)) return false;
        if (!createdBy.equals(graphic.createdBy)) return false;
        if (!dateOfModification.equals(graphic.dateOfModification)) return false;
        if (!modifiedBy.equals(graphic.modifiedBy)) return false;
        return status.equals(graphic.status);
    }

    @Override
    public int hashCode() {
        int result = graphicId;
        result = 31 * result + dateOfCreation.hashCode();
        result = 31 * result + createdBy.hashCode();
        result = 31 * result + dateOfModification.hashCode();
        result = 31 * result + modifiedBy.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (isYear ? 1 : 0);
        return result;
    }
}

enum GraphicStatus {
    CRAEATED("Новый"),
    INPROGRESS("В процессе"),
    FINISHED("Выполненный");

    String graphicStatus;

    private GraphicStatus(String graphicStatus){
        this.graphicStatus = graphicStatus;
    }

    public String getGraphicStatus(){
        return graphicStatus;
    }

}
