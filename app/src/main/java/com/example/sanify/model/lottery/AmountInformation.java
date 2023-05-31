package com.example.sanify.model.lottery;

import android.content.Intent;

public class AmountInformation {
    Integer amount;
    public AmountInformation(Integer amount){
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
