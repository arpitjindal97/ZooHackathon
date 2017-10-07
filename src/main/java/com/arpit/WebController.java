package com.arpit;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Controller
public class WebController {
    static Logger logger = LoggerFactory.getLogger(WebController.class.getName());

    @GetMapping("/")
    public String blank_index() {
        return "upload";
    }

    @RequestMapping(value = {"/uploadAlerts"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadAlerts(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = "/home/pi/Downloads";
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + "Alerts.csv");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                return "File upload success!";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
    }

    @RequestMapping(value = {"/uploadDatabase"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadDatabase(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = "/home/pi/Downloads";
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + "database.json");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                return "Database file successfully updated";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
    }

    @RequestMapping(value = {"/getDatabase"}, method = RequestMethod.GET)
    public void getDatabase(HttpServletResponse response) {

        try {
            File file = new File(Database.path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            response.getOutputStream().print(str);
            response.flushBuffer();
        } catch (Exception e) {
        }
    }
    @RequestMapping(value = {"/startMessages"}, method = RequestMethod.GET)
    @ResponseBody
    public String startMessages()
    {
        Main.processLogFile();
        return "Messages sent successfully";
    }

}
