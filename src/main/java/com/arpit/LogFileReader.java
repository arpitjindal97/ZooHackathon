package com.arpit;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;

public class LogFileReader {

    void startProcess() throws Exception
    {
        String path="/home/pi/Downloads/Alerts.csv";

        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        Main main = new Main();
        main.readySMSInstance();

        String line="";
        while((line = br.readLine())!=null)
        {
            StringBuilder stringBuilder = new StringBuilder(line);
            Log log = new Log();
            log.setName(stringBuilder.substring(0,stringBuilder.indexOf(",")));
            stringBuilder.delete(0,stringBuilder.indexOf(",")+1);

            log.setSerial(stringBuilder.substring(0,stringBuilder.indexOf(",")));
            stringBuilder.delete(0,stringBuilder.indexOf(",")+1);

            log.setTime(stringBuilder.substring(0,stringBuilder.indexOf(",")));
            stringBuilder.delete(0,stringBuilder.indexOf(",")+1);

            log.setDate(stringBuilder.substring(0,stringBuilder.indexOf(",")));
            stringBuilder.delete(0,stringBuilder.indexOf(",")+1);

            log.setLocation(stringBuilder.substring(0,stringBuilder.length()));

            if(log.getTime().equals("") ||
                    log.getDate().equals("") || log.getName().equals(""))
                continue;

            if(log.getName().equals("CAMERA ALERT"))
            {
                log.setLocation(stringBuilder.substring(0,stringBuilder.indexOf(",")));
                stringBuilder.delete(0,stringBuilder.indexOf(",")+1);

                log.setDescription(stringBuilder.substring(0,stringBuilder.length()));
                stringBuilder = new StringBuilder(log.getDescription());
                int one = stringBuilder.indexOf("\"\"")+2;
                int two = stringBuilder.indexOf("\"\"",one);
                log.setDescription(stringBuilder.substring(one,two));
            }
            stringBuilder = new StringBuilder(log.getLocation());

            while(stringBuilder.indexOf("\"\"")!=-1)
            {
                int one = stringBuilder.indexOf("\"\"");
                stringBuilder.replace(one,one+2,"\"");
            }
            if(log.getLocation().contains("GPS"))
            log.setLocation(stringBuilder.substring(1,stringBuilder.length()-1));

            /*ObjectMapper objectMapper = new ObjectMapper();

            String json;
            json = objectMapper.writeValueAsString(log);

            System.out.println(json);*/
            main.send(log);

        }
        br.close();
        fr.close();
    }
}
