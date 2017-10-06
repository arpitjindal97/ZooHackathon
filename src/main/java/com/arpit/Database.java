package com.arpit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

class Database {
    String path = "database.json";

    String[] getNumber(String log_name) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        Recipients recipients = objectMapper.readValue(new File(path), Recipients.class);

        if (log_name.equals("GATE OPEN SENSOR"))
            return recipients.getGate_keeper();
        else if (log_name.equals("WATER HOLE EMPTY"))
            return recipients.getWater_keeper();
        else if (log_name.equals("ELEPHANT TRACKER STATIONARY 24 HOURS ALARM"))
            return recipients.getElephant_keeper();
        else
            return recipients.getRangers();
    }
}