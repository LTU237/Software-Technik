package com.example.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAssetsId implements Serializable {

    @Column(name = "portfolio_id")
    private Integer portfolio_id;

    @Column(name = "asset_id")
    private Integer asset_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioAssetsId that = (PortfolioAssetsId) o;
        return Objects.equals(portfolio_id, that.portfolio_id) &&
                Objects.equals(asset_id, that.asset_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolio_id, asset_id);
    }
}
