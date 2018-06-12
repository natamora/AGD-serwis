package com.ksundaysky.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "component")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "component_id")
    private int id;
    @Column(name = "component_name")
    @NotEmpty(message = "*Uzupełnij nazwę komponentu")
    private String component_name;
    @Column(name = "component_type")
    @NotEmpty(message = "*Uzupełnij rodzaj komponentu")
    private String type;
    @Column(name = "component_price")
    @NotEmpty(message = "*Uzupełnij cenę komponentu")
    private String price;
    @JsonIgnore
    @ManyToMany(mappedBy = "components")
    private Set<Visit> visits;

    public Component() {
    }

    public Set<Visit> getVisits(){
        return visits;
    }

    public void setVisits(Set<Visit> visits){
        this.visits = visits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponent_name() {
        return component_name;
    }

    public void setComponent_name(String component_name) {
        this.component_name = component_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
