package com.ksundaysky.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "workdays")
public class Workdays {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workdays_id")
    private int id;

    @Column(name = "monday")
    private boolean monday;

    @Column(name = "tuesday")
    private boolean tuesday;

    @Column(name = "wednesday")
    private boolean wednesday;

    @Column(name = "thursday")
    private boolean thursday;

    @Column(name = "friday")
    private boolean friday;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isFriday() {
        return friday;
    }
    public boolean isMonday() {
        return monday;
    }
    public boolean isThursday() {
        return thursday;
    }
    public boolean isTuesday() {
        return tuesday;
    }
    public boolean isWednesday() {
        return wednesday;
    }
}
