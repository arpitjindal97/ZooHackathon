package com.arpit;


public class Log {

    private String name,serial,time,location,description="",date;
    public Log(){}

    public Log(String name,String serial,String time,String date,String location,String description)
    {
        this.date=date;
        this.name = name;
        this.time = time;
        this.description = description;
        this.serial = serial;
        this.location = location;
    }

    public void setName(String name){this.name = name;}
    public void setSerial(String serial){this.serial = serial.trim();}
    public void setTime(String time){this.time = time.trim();}
    public void setDescription(String description){this.description = description;}
    public void setLocation(String location){this.location = location;}
    public void setDate(String date){this.date = date;}

    public String getName(){return this.name;}
    public String getTime(){return this.time;}
    public String getSerial(){return this.serial;}
    public String getDescription(){return this.description;}
    public String getLocation(){return this.location;}
    public String getDate(){return this.date;}

}
