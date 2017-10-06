package com.arpit;

public class Recipients {
    private String[] rangers,gate_keeper,
            water_keeper,elephant_keeper;

    public void setRangers(String[] rangers){this.rangers = rangers;}
    public void setGate_keeper(String[] gate_keeper){this.gate_keeper = gate_keeper;}
    public void setWater_keeper(String[] water_keeper){this.water_keeper = water_keeper;}
    public void setElephant_keeper(String[] elephant_keeper){this.elephant_keeper = elephant_keeper;}

    public String[] getRangers(){return rangers;}
    public String[] getGate_keeper(){return gate_keeper;}
    public String[] getWater_keeper(){return water_keeper;}
    public String[] getElephant_keeper(){return elephant_keeper;}
}
