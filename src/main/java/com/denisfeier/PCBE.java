package com.denisfeier;

import com.denisfeier.entity.Buyer;
import com.denisfeier.entity.Seller;
import com.denisfeier.lib.BlockingList;
import com.denisfeier.lib.CustomQueue;
import com.denisfeier.lib.Market;
import com.denisfeier.lib.MarketSingleton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PCBE {
    public static void main(String[] args) throws InterruptedException {
        Market market = MarketSingleton.shared();
        try{
            System.out.println("STARTING");
            Thread.sleep(300);

            initFun(5,5,market);
            Thread.sleep(300);
            market.start();
        }catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("PCBE");
    }

    private static void initFun(int buyersCount,int sellersCount,final Market market){
        List<Seller> sellers = new ArrayList<>();
        List<Buyer> buyers = new ArrayList<>();
        for(int i = 0; i < buyersCount; i++){ buyers.add(new Buyer(market));};
        for(int i = 0; i < sellersCount; i++){ sellers.add(new Seller(market));};

        final Random randomNumber = new Random();
        for(final Seller seller : sellers){ seller.addStock((randomNumber.nextDouble()+1) * 100,new Random().nextInt(20)); }
        for(final Buyer buyer : buyers){ buyer.addDemand((randomNumber.nextDouble()+1) * 100,new Random().nextInt(20));}
    }

}


