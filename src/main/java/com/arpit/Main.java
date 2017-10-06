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

        /*log.info("Starting bot");

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        log.info("Registering Bots");
        try
        {
            botsApi.registerBot(new MySMSBot());
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

        log.info("Fetching details from database.json");
        try
        {
            Database.getDetails(args[0]);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

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
                str="ALERT, ";
            else {
                str = "THREAT ALERT, ";
            }

            str += log.getTime().substring(0,2)+":"+log.getTime().substring(2,4)+", ";
            str+= log.getLocation()+", ";
            str+= log.getDescription()+", "

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
}