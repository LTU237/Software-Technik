package com.example.portfolio.api.dto;

import com.example.portfolio.model.Broker;


public class BrokerDtoOut
{
    int id;
    String company;
    String benutzername;

    public BrokerDtoOut(Broker broker)
    {
        this.company = broker.getCompany();
        this.benutzername = broker.getUsername();
        this.id = broker.getBroker_id();
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
