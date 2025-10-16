package com.example.portfolio.model;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "investor")
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investor_id", nullable = false)
    private int investor_id;

    private String username;
    private String firstname;
    private String lastname;

    @Column(name = "password_hash", nullable = false, columnDefinition = "BINARY")
    private byte[] password_hash;

    @Column(name = "password_salt", nullable = false, columnDefinition = "BINARY")
    private byte[] password_salt;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Portfolio> portfolios = new TreeSet<>();

    public Investor(String firstname, String lastname, String username, byte[] password_hash, byte[] password_salt) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password_hash = password_hash;
        this.password_salt = password_salt;
    }


    public void addPortfolio(Portfolio portfolio) {
        portfolios.add(portfolio);
    }

    public Set<Portfolio> getPortfolios() {
        return Collections.unmodifiableSet(portfolios);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return investor_id == investor.investor_id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(investor_id);
    }

    @Override
    public String toString()
    {
        return "Investor{" +
                "firstname='" + firstname + '\'' +
                "lastname='" + lastname + '\'' +
                '}';
    }
}
