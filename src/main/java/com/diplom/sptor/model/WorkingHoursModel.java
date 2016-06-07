package com.diplom.sptor.model;

import com.diplom.sptor.domain.Equipment;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

/**
 * Created by user on 13.04.2016.
 */
public class WorkingHoursModel {

    @NotEmpty
    private int equipment;

    @DecimalMax(value = "100.0", message = "Максимально допустимое значение: 100")
    @DecimalMin(value = "0.0", message = "Минимально допустимое значение: 0")
    @NotEmpty(message = "Пожалуйста введите наработку")
    private double value;

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
