package org.launchcode.PickMyFood.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class Item implements Comparable<Item>{

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Menu menu;

    @ManyToMany(mappedBy = "items")
    private List<Location> locations;

    @NotNull
    private Integer number;

    @NotNull
    private String other;

    @NotNull
    private boolean inInventory = true;

    private Long userId;

    public Item(Menu menu, Integer number, String other, long userId) {
        this.menu = menu;
        this.number = number;
        this.other = other;
        this.userId = userId;
    }

    public Item(Menu menu, Integer number, long userId) {
        this.menu = menu;
        this.number = number;
        this.userId = userId;
    }

    public Item(){}

    public Item(long userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public boolean isInventory() {
        return inInventory;
    }

    public void setisInventory(boolean in) {
        this.inInventory = in;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) { this.locations = locations; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(Item o){
        int compare = menu.getName().compareTo(o.getMenu().getName());
        if (compare == 0) {
            compare = number.compareTo(o.getNumber());
        }
        return compare;
    }


}