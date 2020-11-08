package com.denisfeier.entity;

import com.denisfeier.lib.Market;

public class Seller extends Person {

    public Seller(Market market) {
        super(market);
    }

    @Override
    public void addToHistory(StockElement stockElement, int amount) {

    }

    public void addStock(double price, int amount) {
        Stock stock = new Stock(price, amount, this);
        new Thread(() -> {
                try {
                    this.market.addStock(stock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            Thread.currentThread().interrupt();
        }).start();

    }
}
