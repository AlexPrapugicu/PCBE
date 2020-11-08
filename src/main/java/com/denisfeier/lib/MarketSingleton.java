package com.denisfeier.lib;

//import com.denisfeier.entity.Demand;
//import com.denisfeier.entity.Stock;

public class MarketSingleton {
    private static Market market = null;

    public static Market shared() {
        if(market == null) {
            market =  new Market();
            market.setThreadPool();
            market.setActiveLogger(true);
//            market.setActiveMarket(true);
        }
        return market;
    }
}