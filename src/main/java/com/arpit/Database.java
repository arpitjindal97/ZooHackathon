package com.arpit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

class Database {
    static String path = "/home/pi/Downloads/database.json";

    String[] getNumber(String log_name) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        Recipients recipients = objectMapper.readValue(new File(path), Recipients.class);

        if (log_name.equals("GATE OPEN SENSOR"))
            return recipients.getGate_keeper();
        else if (log_name.equals("WATER HOLE EMPTY"))
            return recipients.getWater_keeper();
        else if (log_name.equals("ELEPHANT TRACKER STATIONARY 24 HOURS ALARM"))
            return recipients.getElephant_keeper();
        else if (log_name.equals("CAMERA ALERT") ||log_name.equals("GROUND SENSOR ALERT")
                || log_name.equals("RANGER EMERGENCY ALERT"))
            return recipients.getRangers();
        else
        {
            return recipients.getAll();
        }
    }
}