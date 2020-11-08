package com.denisfeier.lib;

import com.denisfeier.entity.Demand;
import com.denisfeier.entity.Stock;

import java.util.ArrayList;

public class Market {
    private ThreadPool threadPool;

    private final BlockingList<Stock> StockList = new BlockingList<>(5,new ArrayList<>());
    private final BlockingList<Demand> DemandList= new BlockingList<>(5,new ArrayList<>());
    private final BlockingList<String> HistoryList = new BlockingList<>(10,new ArrayList<>());

    private boolean activeMarket= false;
    private boolean activeLogger = false;

    public Market(){}

    public void setActiveMarket(boolean activeMarket) {
        this.activeMarket = activeMarket;
    }

    public void setActiveLogger(boolean activeLogger) {
        this.activeLogger = activeLogger;
    }

    public void setThreadPool() {
        this.threadPool = new ThreadPool(3);
    }

    public void addStock(Stock stock) throws InterruptedException {
            this.StockList.put(stock);
        this.HistoryList.put(stock.getOwner().getId() + " added a new stock");
    }

    public void removeStock(Stock stock) throws InterruptedException {
            this.StockList.take(stock);
        this.HistoryList.put(stock.getID() + " was removed");
    }

    public void addDemand(Demand demand) throws InterruptedException {
        this.DemandList.put(demand);
        this.threadPool.doTask(() -> {
            try{
                match(demand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.HistoryList.put(demand.getOwner().getId() + " added a new demand");
    }

    public void removeDemand(Demand demand) throws InterruptedException {
        this.DemandList.take(demand);
        System.out.println("removed demand!");
        this.HistoryList.put(demand.getID() + " was removed");
    }

    public void match(Demand demand) throws InterruptedException {
        int trials = 0;

        while (true){
            for(int i = 0; i < this.StockList.getListSize(); i++){
                if(this.StockList.getElement(i).getCount() <=0){
                    this.removeStock(this.StockList.getElement(i));
                    return;
                }
                if(demand.getCount() <= 0 ){
                    this.removeDemand(demand);
                    return;
                }
                // atata poti sa cumperi max;
                int meetsCount = Math.min(demand.getCount(), this.StockList.getElement(i).getCount());
                boolean meetsPrice = demand.getPrice() >= this.StockList.getElement(i).getPrice();
                if(meetsPrice){
                        this.StockList.getElement(i).use(meetsCount);
                        System.out.println("Transaction between "
                                + demand.getOwner().getId()
                                + " and "
                                + this.StockList.getElement(i).getOwner().getId()
                                + "\n") ;
                        int indexOfDemand = this.DemandList.getIndexOfElement(demand);
                        this.DemandList.getElement(indexOfDemand).use(meetsCount);
                }
            }
            if(++trials >= 200){ return; }
        }
    }

    public void stop() throws Exception {
        if(!this.activeMarket){throw new Exception("Already stopped");}
        this.activeMarket = false;
        this.threadPool.stopPool();
    }
    public void start() throws Exception {
        if(this.activeMarket){throw new Exception("Already running!");}
        this.activeMarket = true;
        this.threadPool.refreshPool();
    }
}
