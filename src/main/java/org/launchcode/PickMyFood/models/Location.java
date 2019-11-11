package org.launchcode.PickMyFood.models;

/**
 * created By O.J Shin
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location implements Comparable<Location> {

    @Id
    @GeneratedValue
    private int id;

    @NotNull(message = "location muse have a name")
    private String name;

    @NotNull(message = "location must have a location number")
    @Min(1)
    @Max(200)
    private Integer number;

    @NotNull
    private String safe;

    @ManyToMany
    private List<Item> items = new ArrayList<>();


    @ManyToMany
    private List<Task> tasks = new ArrayList<>();

    private Long userId;

    public Location(){}

    public Location(String name, Integer number, long userId) {
        this.name = name;
        this.number = number;
        this.userId = userId;
    }

    public Location(String name, Integer number, String safe, long userId) {
        this.name = name;
        this.number = number;
        this.safe = safe;
        this.userId = userId;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);}

    public void addTask(Task task) {tasks.add(task);}

    public void removeTask(Task task) {tasks.remove(task);}

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

    public List<Item> getItems() {
        return items;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(Location o) {
        int compare = name.compareTo(o.getName());
        return compare;
    }

}

