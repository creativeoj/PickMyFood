package org.launchcode.PickMyFood.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Histories {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 50, message = "history must be between 1 and 50 characters")
    private String type;

    private String name;

    private String item;

    private String status;

    private Integer number;

    private Date myDate;

    private Long userId;

    public Histories() {

    }

    public Histories(Date myDate) {
        this.myDate = new Date();
    }

    public Histories(String type, String item, String status) {
        this.type = type;
        this.item = item;
        this.status = status;
        this.myDate = new Date();
    }

    public Histories(String type, String item, String status, Integer number) {
        this.type = type;
        this.item = item;
        this.status = status;
        this.number = number;
        this.myDate = new Date();
    }

    public Histories(String type, String name, String item, String status, Integer number) {
        this.type = type;
        this.name = name;
        this.item = item;
        this.status = status;
        this.number = number;
        this.myDate = new Date();
    }

    public Histories(String type, String item, String status, Long userId) {
        this.type = type;
        this.item = item;
        this.status = status;
        this.myDate = new Date();
        this.userId = userId;
    }


    public Histories(String type, String item, String status, Integer number, Long userId) {
        this.type = type;
        this.item = item;
        this.status = status;
        this.number = number;
        this.userId = userId;
        this.myDate = new Date();
    }

    public Histories(String type, String name, String item, String status, Integer number, Long userId) {
        this.type = type;
        this.name = name;
        this.item = item;
        this.status = status;
        this.number = number;
        this.myDate = new Date();
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getMyDate() {
        return myDate;
    }

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}