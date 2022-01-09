package com.epam.auction;

import com.epam.auction.entity.Participant;
import com.epam.auction.entity.ParticipantWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final String PASSENGERS_PATH = "src/main/resources/data.json";
    public static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ParticipantWrapper participantWrapper = objectMapper.readValue(new File(PASSENGERS_PATH),
                ParticipantWrapper.class);
        List<Participant> participants = participantWrapper.getParticipants();
        ExecutorService executorService = Executors.newFixedThreadPool(participants.size());
        for (Participant participant : participants){
            executorService.submit(participant);
        }
        executorService.shutdown();
    }
}
