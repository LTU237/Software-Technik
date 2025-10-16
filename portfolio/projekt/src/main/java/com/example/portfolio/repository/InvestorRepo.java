package com.example.portfolio.repository;

import com.example.portfolio.model.Investor;
import com.example.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorRepo extends JpaRepository<Investor, Integer>, InvestorRepoJPA {
    Investor findByUsername(String username);

    @Query("select i from Investor i")
    List<Investor> getAll();

    @Query("select i from Investor i where i.username = ?1")
    Investor getInvestorByUsername(String username);

    @Query("select i from Investor i where i.investor_id = ?1")
    Investor getInvestorById(int id);


}
