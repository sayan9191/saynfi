package com.example.sanify.model.lottery;

public class LotteryInformation {
    String name;
    String number;

    public LotteryInformation(String name, String number) {
        this.name = name;
        this.number = number;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
