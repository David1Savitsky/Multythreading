package com.epam.auction.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auction {
    private static Auction instance;
    private static Semaphore auctionSemaphore = new Semaphore(5);;
    private static final Lock auctionLock = new ReentrantLock();
    public static final Integer LOTS_AMOUNT = 3;

    private static List<Lot> lots;
    public static final Logger LOGGER = LogManager.getLogger();

    private Auction(){
        lots = new ArrayList<>(LOTS_AMOUNT);
        for (int i = 0; i < LOTS_AMOUNT; i++){
            lots.add(new Lot());
        }
    }

    public static Auction getInstance(){
        Auction localInstance = instance;
        if (localInstance == null){
            auctionLock.lock();
            try {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Auction();
                }
            }
            finally{
                auctionLock.unlock();
            }
        }
        return localInstance;
    }

    public void process(Participant participant) {
        try {
            auctionSemaphore.acquire();
            auctionLock.lock();
            Lot currentLot = lots.get(participant.getIndexLot() - 1);
            int participantMoney = participant.getMoney();
            if (currentLot.getBet() < participantMoney){
                currentLot.setBet(participantMoney);
                LOGGER.log(Level.INFO, String.format("Bet is made by %s", participant.getName()));
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e.getCause());
        } finally {
            auctionSemaphore.release();
            auctionLock.unlock();
        }
    }
}
