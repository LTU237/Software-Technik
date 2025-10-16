package com.example.portfolio.repository;

import com.example.portfolio.model.Broker;

public interface BrokerRepoJPA {
    public Broker createBroker(String username, String company, String password);
    public Broker update(int id, String company, String password);
    public Broker mergeAndRefresh(Broker broker);
    public void delete(int id);
}
