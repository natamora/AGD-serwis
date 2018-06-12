package com.ksundaysky.model.wrapper;

import com.ksundaysky.model.Component;
import com.ksundaysky.model.Visit;

import java.util.ArrayList;
import java.util.List;

public class VisitComponents  {
    Visit visit;
    ArrayList<SelectableComponent> components = new ArrayList<SelectableComponent>();

    public VisitComponents() {

    }

    public VisitComponents(Visit visit, List<Component> components1) {
        this.visit = visit;
        this.components = new ArrayList<>();
        this.setComponents(components1);
        for(SelectableComponent c : components){
            if(this.visit.getComponents() != null) {
                for(Component ci : this.visit.getComponents()) {
                    if(ci.getId() == c.getId()) {
                        c.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public List<SelectableComponent> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components1) {
        for(Component comp : components1){
            components.add(new SelectableComponent(comp));
        }
    }
}
