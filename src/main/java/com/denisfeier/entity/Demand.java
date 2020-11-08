package com.denisfeier.entity;

public class Demand extends StockElement {

    public Demand(double price, int count, Person owner) {
        super(price, count, owner);
    }

    @Override
    public void use(int count) {
        this.setCount(this.getCount() - count);
        System.out.println("updated demand "
                + this.getID()
                + " count: "
                + this.getCount());
//        this.getOwner().addToHistory(this, count);
    }
}
