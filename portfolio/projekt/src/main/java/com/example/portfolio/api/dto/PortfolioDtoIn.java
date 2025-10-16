package com.example.portfolio.api.dto;

import com.example.portfolio.model.Broker;
import com.example.portfolio.model.Investor;

import java.time.LocalDateTime;

public class PortfolioDtoIn {
    int brokerId;
    int investorId;

    public PortfolioDtoIn(int brokerId, int investorId) {
        this.brokerId = brokerId;
        this.investorId = investorId;
    }

    public PortfolioDtoIn() {
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public int getInvestorId() {
        return investorId;
    }

    public void setInvestorId(int investorId) {
        this.investorId = investorId;
    }
}

