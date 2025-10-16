package com.example.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio_assets")
public class Portfolio_Assets {

    @EmbeddedId
    private PortfolioAssetsId portfolioAssetsId;

    @ManyToOne
    @MapsId("portfolio_id")
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @MapsId("asset_id")
    @JoinColumn(name = "asset_id")
    private Asset asset;
}

