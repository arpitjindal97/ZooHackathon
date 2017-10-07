package com.arpit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
/*import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
*/
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;

public class Main {
    private SendSMS sendSMS;
    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {

        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid.substring(0, pid.indexOf("@")));

        //WebController.startServer();*/

        processLogFile();

    }

    void readySMSInstance() {
        log.info("Creating SMS Instance");
        sendSMS = new SendSMS();
        if (sendSMS.selectedPort == null) {
            log.info("Port not recognized");
            System.exit(1);
        }
        log.info("Port Desc : " + sendSMS.selectedPort.getDescriptivePortName());
        log.info("Port Number : " + sendSMS.selectedPort.getSystemPortName());
    }

    void send(Log log) {
        Database database = new Database();
        try {

            String[] array = database.getNumber(log.getName());
            //System.out.println(Arrays.toString(array) + "\t" + log.getDescription());

            String str = "";
            if(log.getName().contains("ALERT"))
                str="THREAT ALERT, ";
            else {
                str = "ALERT, ";
            }

            str += log.getTime().substring(0,2)+":"+log.getTime().substring(2,4)+" HRS , ";
            str+= log.getLocation()+", ";

            str += getReaction(log);

            for(int i=0;i<array.length;i++) {
                boolean result = sendSMS.sendOne(array[i], str);

                while (!result) {
                    readySMSInstance();
                    result = sendSMS.sendOne(array[i], str);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void processLogFile() {
        LogFileReader logFileReader = new LogFileReader();
        try {
            logFileReader.startProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getReaction(Log log)
    {
        String str = log.getName();
        if(str.equals("CAMERA ALERT"))
            return log.getDescription()+", Rangers on Alert, Track the intruders";
        else if(str.equals("GROUND SENSOR ALERT"))
            return "Sensed some foots, Rangers on Alert, Find who was that";
        else if(str.equals("RANGER EMERGENCY ALERT"))
            return "All Units on High Alert, Quick reaction force to track and intercept";
        else if(str.equals("GATE OPEN SENSOR"))
            return "Gates opened, Gatekeepers on High Alert, Quickly reach to the Doors";
        else if(str.equals("WATER HOLE EMPTY"))
            return "Water pool empty, Care takers on alert, Quickly fill the tank";
        else if(str.equals("ELEPHANT TRACKER STATIONARY 24 HOURS ALARM"))
            return "Elephant hasn't moved, Care taker on alert, Find whats the issue";
        else return "Unknown Alert, All units on alert, Everyone check if everythin is fine";
    }
}