package com.example.portfolio.service;

import com.example.portfolio.model.*;
import com.example.portfolio.repository.BrokerRepo;
import com.example.portfolio.repository.InvestorRepo;
import com.example.portfolio.repository.PortfolioRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepository;
    private InvestorRepo investorRepository;
    private BrokerRepo brokerRepository;

    @Autowired
    public PortfolioService(PortfolioRepo portfolioRepository, InvestorRepo investorRepository, BrokerRepo brokerRepository) {
        this.portfolioRepository = portfolioRepository;
        this.investorRepository = investorRepository;
        this.brokerRepository = brokerRepository;
    }


    //Portfolios
    public Portfolio addPortfolio(Investor investor, Broker broker){
        Portfolio portfolio = new Portfolio(investor, broker);
        investor.addPortfolio(portfolio);

        investorRepository.mergeAndRefresh(investor);

        return portfolio;
    }

    public Portfolio createPortfolio(int investorId, int brokerId, LocalDateTime creation_date) {
        Investor investor = investorRepository.getInvestorById(investorId);
        Broker broker= brokerRepository.getById(brokerId);
        Portfolio portfolio = new Portfolio(investor, broker);

        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getPortfoliosByInvestorId(int investorId) {
        return portfolioRepository.getPortfoliosByInvestorId(investorId);
    }

    public Portfolio getPortfolioById(int id) {
        return portfolioRepository.getPortfolioById(id);
    }

    public List<Portfolio> getPortfoliosByBrokerId(int brokerId) {
        return portfolioRepository.getPortfoliosByBrokerId(brokerId);
    }

    public void deletePortfolio(int portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }
}
