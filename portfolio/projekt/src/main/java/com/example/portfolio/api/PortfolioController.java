package com.example.portfolio.api;

import com.example.portfolio.api.dto.PortfolioDtoIn;
import com.example.portfolio.api.dto.PortfolioDtoOut;
import com.example.portfolio.api.security.AccessToken;
import com.example.portfolio.model.Investor;
import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.exception.NotFoundException;
import com.example.portfolio.service.InvestorService;
import com.example.portfolio.service.PortfolioService;

import com.example.portfolio.api.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final SecurityManager securityManager;
    private final InvestorService investorService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, SecurityManager securityManager, InvestorService investorService) {
        this.portfolioService = portfolioService;
        this.securityManager = securityManager;
        this.investorService = investorService;
    }




    // READ by Broker --le broker ne voit pas les portfolios
    @GetMapping("/broker/{brokerId}")
    public ResponseEntity<Collection<PortfolioDtoOut>> getPortfoliosByBroker(@RequestHeader AccessToken accessToken, @PathVariable int brokerId) {
        checkAnmeldung(accessToken);
        try{
        Collection<Portfolio> portfolios = portfolioService.getPortfoliosByBrokerId(brokerId);

        return ResponseEntity.ok(
                portfolios.stream().map(PortfolioDtoOut::new).collect(Collectors.toList())
        );
    } catch (NotFoundException exception) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
    }
    }

    // DELETE
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@RequestHeader AccessToken accessToken, @PathVariable int portfolioId) {
        checkAnmeldung(accessToken);

        try {
            portfolioService.deletePortfolio(portfolioId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    private void checkAnmeldung(AccessToken accessToken) {
        if (!securityManager.hasAccess(accessToken) || !securityManager.hasInvestorAccess(accessToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
    }

    private void checkBerechtigung(AccessToken accessToken, int brokerId, int investorId )
    {
        if( (securityManager.getInvestor(accessToken).getInvestor_id() != investorId) && (securityManager.getBroker(accessToken).getBroker_id() != brokerId) )
        {
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Benutzer hat keine Berechtigung");
        }
    }
}
