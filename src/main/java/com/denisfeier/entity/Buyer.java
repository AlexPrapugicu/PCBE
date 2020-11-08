package com.denisfeier.entity;

import com.denisfeier.lib.Market;

public class Buyer extends Person {

    public Buyer(Market market) {
        super(market);
    }

    @Override
    public void addToHistory(StockElement stockElement, int amount) {

    }

    public void addDemand(double price, int amount) {
        Demand demand = new Demand(price,amount,this);
        new Thread(()-> {
                try {
                    this.market.addDemand(demand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            Thread.currentThread().interrupt();
        }).start();
    }

}
