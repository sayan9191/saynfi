package com.example.sanify.model.lottery;

public class LotteryInformation {
    String rank;
    String prizeMoney;
    String ticketNo;

    public LotteryInformation(String rank, String prizeMoney, String ticketNo) {
        this.rank = rank;
        this.prizeMoney = prizeMoney;
        this.ticketNo = ticketNo;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(String prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
}
