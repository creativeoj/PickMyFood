package org.launchcode.PickMyFood.models.forms;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class SearchItemForm {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private Integer number;

    public SearchItemForm(Integer number){
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
