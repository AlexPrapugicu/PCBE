package com.denisfeier.entity;

public class Stock extends StockElement{

    public Stock(double price, int count, Person owner) {
        super(price, count, owner);
    }

    @Override
    public void use(int count) {
        this.setCount(this.getCount() - count);
        this.getOwner().addToHistory(this, count);
    }
}
