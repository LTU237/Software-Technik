package com.example.portfolio.service;

import com.example.portfolio.model.Broker;
import com.example.portfolio.repository.BrokerRepo;
import com.example.portfolio.repository.PortfolioRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.portfolio.model.Investor;
import com.example.portfolio.repository.InvestorRepo;

import java.util.List;

@Service
@Transactional
public class InvestorService {


    private final InvestorRepo investorRepository;
    private final BrokerRepo brokerRepository;
    private final PortfolioRepo portfolioRepo;

    @Autowired
    public InvestorService(InvestorRepo investorRepository, BrokerRepo brokerRepository, PortfolioRepo portfolioRepo){
        this.investorRepository=investorRepository;
        this.brokerRepository = brokerRepository;
        this.portfolioRepo = portfolioRepo;
    }

    public Investor getInvestorByUsername(String username)
    {
        return  investorRepository.getInvestorByUsername(username);
    }

    public Investor createInvestor(String firstname, String lastname, String username, String password) {
        return investorRepository.createInvestor(firstname, lastname, username, password);
    }

    public Investor updateInvestor(int id, String firstname, String lastname, String password) {
        return investorRepository.update(id, firstname, lastname, password);
    }
    public void deleteInvestor(int id) {
         investorRepository.delete(id);
        return;
    }
    public Broker getBrokerById(int id) {
        return brokerRepository.getById(id);
    }

    public List<Broker> getAllBrokers() {
        return brokerRepository.getAll();
    }


}
