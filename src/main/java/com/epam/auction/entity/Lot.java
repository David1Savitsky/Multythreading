package com.epam.auction.entity;

public class Lot {
    private int bet;

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Lot lot = (Lot) o;
        return bet == lot.bet;
    }

    @Override
    public int hashCode() {
        int prime = 17;
        prime = 31 * prime + bet;
        return prime;
    }
}
