package com.example.portfolio.repository;

import com.example.portfolio.model.Broker;
import com.example.portfolio.model.Investor;
import com.example.portfolio.model.Portfolio;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PortfolioRepoJPAImpl implements  PortfolioRepoJPA{

    /*EntityManager entityManager;

    @Autowired
    public PortfolioRepoJPAImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Portfolio createPortfolio(int investorId, int brokerId, LocalDateTime creation_date) {
        Portfolio portfolio= new Portfolio(investorId, brokerId);
        entityManager.persist(portfolio);
        entityManager.flush();
        entityManager.refresh(portfolio);
        return portfolio;
    }

    @Override
    public Portfolio update(int id, LocalDateTime creation_date) {
        Portfolio portfolio = entityManager.find(Portfolio.class, id);

        portfolio.setPortfolio_id( id );
        portfolio.setCreation_date(creation_date);

        entityManager.merge(portfolio);
        entityManager.flush();
        entityManager.refresh(portfolio);

        return portfolio;
    }

    @Override
    public Portfolio mergeAndRefresh(Portfolio portfolio) {
        entityManager.persist(portfolio);
        entityManager.flush();
        entityManager.refresh(portfolio);

        return portfolio;
    }

    @Override
    public void delete(int id) {
        Portfolio portfolio = entityManager.find(Portfolio.class, id);
        entityManager.remove(portfolio);
        return;
    }*/
}
