package org.launchcode.PickMyFood.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "menu_id")
    private List<Item> items = new ArrayList<>();

    private Long userId;

    public Menu() {}

    public Menu(long userId) {
        this.userId = userId;
    }

    public Menu(String name, Long userId) {this.name = name; this.userId = userId;}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  List<Item> getItem() {return items;}

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
