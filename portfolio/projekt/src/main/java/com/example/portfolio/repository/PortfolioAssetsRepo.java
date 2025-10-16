package com.example.portfolio.repository;

import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.PortfolioAssetsId;
import com.example.portfolio.model.Portfolio_Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioAssetsRepo extends JpaRepository<Portfolio_Assets, PortfolioAssetsId>, PortfolioRepoJPA {

    @Query("select p from Portfolio p where p.investor.investor_id = ?1")
    List<Portfolio> getPortfoliosByInvestorId(int investorId);

    @Query("select p from Portfolio p where p.broker.broker_id = ?1")
    List<Portfolio> getPortfoliosByBrokerId(int brokerId);
}
