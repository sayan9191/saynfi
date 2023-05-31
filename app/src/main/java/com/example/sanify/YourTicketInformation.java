package com.example.sanify;

public class YourTicketInformation {
    String date, time, ticketNo;

    public YourTicketInformation(String date, String time, String ticketNo) {
        this.date = date;
        this.time = time;
        this.ticketNo = ticketNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
}
