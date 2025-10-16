package com.example.portfolio.api.dto;

import com.example.portfolio.model.Portfolio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
@Setter
@Getter
public class PortfolioDtoOut
{
    private static final DateTimeFormatter fomatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale( Locale.forLanguageTag("de"));

    private final String formattedDateTime;
    private int portfolioId;
    private int investorId;
    private int brokerId;

    public PortfolioDtoOut(LocalDateTime dateTime) {
        this.formattedDateTime = fomatter.format(dateTime);
    }

    public PortfolioDtoOut(Portfolio portfolio)
    {
        this.formattedDateTime= fomatter.format(portfolio.getCreation_date());
        this.portfolioId = portfolio.getPortfolio_id();
        this.investorId = portfolio.getInvestor().getInvestor_id();
        this.brokerId = portfolio.getBroker().getBroker_id();
    }

}
