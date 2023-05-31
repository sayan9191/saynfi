package com.example.sanify.model.lottery;

public class PrizePoolInformation {
    String rank, ticketNo, prizeMoney;

    public PrizePoolInformation(String rank, String ticketNo, String prizeMoney) {
        this.rank = rank;
        this.ticketNo = ticketNo;
        this.prizeMoney = prizeMoney;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(String prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
}
