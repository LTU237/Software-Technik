package com.example.portfolio.service;

import com.example.portfolio.model.PortfolioAssetsId;
import com.example.portfolio.model.Portfolio_Assets;
import com.example.portfolio.repository.PortfolioAssetsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioAssetsService {

    @Autowired
    private PortfolioAssetsRepo portfolioAssetsRepository;

    /*public List<Portfolio_Assets> getAllPortfolioAssets() {
        return portfolioAssetsRepository.getAll();
    }

    public Portfolio_Assets getPortfolioAssetsById(PortfolioAssetsId id) {
        return portfolioAssetsRepository.getPortfolio_AssetsById(id);
    }

    public Portfolio_Assets savePortfolioAssets(Portfolio_Assets portfolioAssets) {
        return portfolioAssetsRepository.update(portfolioAssets);
    }
*/
    public void deletePortfolioAssets(PortfolioAssetsId id) {
        portfolioAssetsRepository.deleteById(id);
    }
}
