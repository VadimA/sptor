package com.diplom.sptor.planning.domain;

/**
 * Created by user on 12.02.2018.
 */
public class DayPilot {

    private String name;

    private int id;

    public DayPilot() {
    }

    public DayPilot(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayPilot dayPilot = (DayPilot) o;

        if (id != dayPilot.id) return false;
        return name.equals(dayPilot.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }
}
