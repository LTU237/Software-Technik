package com.example.portfolio.model;

import jakarta.persistence.*;

import java.util.*;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "broker")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_id")
    private int broker_id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "password_hash", nullable = false)
    private byte[] passwordHash;

    @Column(name = "password_salt", nullable = false)
    private byte[] passwordSalt;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Portfolio> portfolios = new TreeSet<>();

    @OneToMany(mappedBy = "broker",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Asset> assets = new TreeSet<>();

    public Broker( String username, String company, byte[] passwordHash, byte[] passwordSalt) {
        this.username = username;
        this.company = company;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }


    //Getters Setters
    public Set<Asset> getAssets() {
        return assets;
    }

    public void addAssets(Asset asset) {
        assets.add(asset);
    }

    public int getBroker_id() {
        return broker_id;
    }

    public void setBroker_id(int broker_id) {
        this.broker_id = broker_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Set<Portfolio> getPortfolios() {
        return Collections.unmodifiableSet(portfolios);
    }

    public void addPortfolio(Portfolio portfolio) {
        portfolios.add(portfolio);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broker broker = (Broker) o;
        return broker_id == broker.broker_id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(broker_id);
    }

    @Override
    public String toString()
    {
        return "Broker{" +
                "company='" + company + '\'' +
                '}';
    }
}
