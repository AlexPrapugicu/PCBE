package com.denisfeier.entity;

import com.denisfeier.lib.Market;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public abstract class Person {
    protected final Market market;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person(Market market) {
        this.market = market;
        this.id = UUID.randomUUID().toString().replace("-", "");;
    }

    public abstract void addToHistory(StockElement stockElement, int amount);

}
