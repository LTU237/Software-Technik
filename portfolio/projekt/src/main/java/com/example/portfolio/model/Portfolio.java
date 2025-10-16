package com.example.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "portfolio", uniqueConstraints = {@UniqueConstraint(columnNames = {"investor_id", "broker_id"})})
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private int portfolio_id;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = false)
    private Broker broker;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creation_date;


    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Portfolio_Assets> portfolio_assets;//=new TreeSet<>();

    public Portfolio(Investor investor, Broker broker) {
        this.investor = investor;
        this.broker = broker;
        this.creation_date = LocalDateTime.now();
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public Set<Portfolio_Assets> getPortfolio_assets() {
        return Collections.unmodifiableSet(portfolio_assets);
    }

    public void setPortfolioAssets(Set<Portfolio_Assets> portfolioAssets) {
        this.portfolio_assets = portfolioAssets;
    }
    public void addPortfolio_assets(Portfolio_Assets portfolio_asset) { //Not sure
        portfolio_assets.add(portfolio_asset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return portfolio_id == portfolio.portfolio_id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(portfolio_id);
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "broker=" + broker +
                ", creation_date=" + creation_date +
                ", investor=" + investor +
                ", portfolio_assets=" + portfolio_assets +
                '}';
    }
}

