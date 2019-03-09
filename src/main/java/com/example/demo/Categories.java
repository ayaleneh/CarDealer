package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Categories")
public class Categories {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Car> cars;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
