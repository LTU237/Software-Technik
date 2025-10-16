package com.example.portfolio.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private int assetId;

    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = false)
    private Broker broker;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private EnumAsset kind;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "asset")
    private Set<Portfolio_Assets> portfolioAssets;

    public Asset() {

    }

    public Asset(Broker broker, EnumAsset kind, String name) {
        this.broker = broker;
        this.kind = kind;
        this.name = name;
    }


    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public EnumAsset getKind() {
        return kind;
    }

    public void setKind(EnumAsset kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Portfolio_Assets> getPortfolioAssets() {
        return portfolioAssets;
    }

    public void setPortfolioAssets(Set<Portfolio_Assets> portfolioAssets) {
        this.portfolioAssets = portfolioAssets;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Asset asset = (Asset) o;
        return assetId == asset.assetId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(assetId);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "broker=" + broker +
                ", kind=" + kind +
                ", name='" + name + '\'' +
                ", portfolioAssets=" + portfolioAssets +
                '}';
    }
}
