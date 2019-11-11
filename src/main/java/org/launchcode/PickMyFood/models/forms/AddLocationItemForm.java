package org.launchcode.PickMyFood.models.forms;

import org.launchcode.PickMyFood.models.Item;
import org.launchcode.PickMyFood.models.Location;

import javax.validation.constraints.NotNull;

public class AddLocationItemForm {

    @NotNull
    private int locationId;

    @NotNull
    private int itemId;

    private Iterable<Item> items;

    private Location location;

    public AddLocationItemForm(Iterable<Item> items, Location location){
        this.items = items;
        this.location = location;
    }

    public AddLocationItemForm() {}

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Iterable<Item> getItems() {
        return items;
    }

    public Location getLocation() {
        return location;
    }
}