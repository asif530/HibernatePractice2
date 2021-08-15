package org.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
* This annotation is mandatory if we want to use thia object in an entity
* These are called object values
* */
@Embeddable
public class Address {
    /*Now what this did is when we will use this object in an entity and try to save it,it will take Rasta in place of street.*/
    @Column(name="rasta")
    private String street;
    private String city;
    private String location;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
