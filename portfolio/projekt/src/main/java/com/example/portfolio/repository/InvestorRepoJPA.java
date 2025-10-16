package com.example.portfolio.repository;

import com.example.portfolio.model.Investor;

public interface InvestorRepoJPA {
   public Investor createInvestor(String firstname, String lastname, String username,String password);
    public Investor update(int id, String firstname, String lastname, String password);
    public Investor mergeAndRefresh(Investor investor);
    public void delete(int id);
}
