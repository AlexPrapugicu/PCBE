package com.denisfeier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
public abstract class StockElement {
    private String ID;
    private double price;
    private int count;


    private Person owner;

    public StockElement(double price, int count, Person owner) {
        this.ID = UUID.randomUUID().toString();
        this.price = price;
        this.count = count;
        this.owner = owner;
    }
    public String getID() {
        return ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }


    public abstract void use(int count);
}
