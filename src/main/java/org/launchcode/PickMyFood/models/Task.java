package org.launchcode.PickMyFood.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class Task {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 500)
    private String name;

    private Long userId;

    @ManyToMany(mappedBy = "tasks")
    private List<Location> locations;

    public Task() {}

    public Task(String name, long userId) {
        this.name = name;
        this.userId = userId;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}