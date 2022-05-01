package com.epam.auction.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant implements Runnable {
    @JsonProperty
    private String name;
    @JsonProperty
    private int money;
    @JsonProperty
    private int indexLot;

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getIndexLot() {
        return indexLot;
    }

    @Override
    public void run() {
        Auction auction = Auction.getInstance();
        auction.process(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Participant participant = (Participant) o;
        return money == participant.money && indexLot == participant.indexLot && name.equals(participant.name);
    }

    @Override
    public int hashCode() {
        int prime = 17;
        prime = prime * 31 + name.hashCode();
        prime = prime * 31 + money;
        prime = prime * 31 + indexLot;
        return prime;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name=" + name +
                ", money=" + money +
                ", indexLot=" + indexLot +
                '}';
    }
}
