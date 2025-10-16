package com.example.portfolio.api;

import com.example.portfolio.api.dto.*;
import com.example.portfolio.api.security.AccessToken;
import com.example.portfolio.api.security.SecurityManager;
import com.example.portfolio.model.Investor;
import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.exception.NotFoundException;
import com.example.portfolio.service.InvestorService;
import com.example.portfolio.service.PortfolioService;
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
@RequestMapping("/investors")
public class InvestorController {

    private final InvestorService investorService;
    private final SecurityManager securityManager;
    private final PortfolioService portfolioService;

    @Autowired
    public InvestorController(InvestorService investorService, SecurityManager securityManager, PortfolioService portfolioService)
    {
        this.securityManager = securityManager;
        this.investorService = investorService;
        this.portfolioService = portfolioService;
    }

    //CREATE Investor
    @PostMapping
    public ResponseEntity<InvestorLoginDtoOut> createInvestorCon(@RequestBody InvestorCreateDtoIn investorCreateDtoIn)
    {
        try {
            Investor investor = investorService.createInvestor(investorCreateDtoIn.getFirstname(),
                    investorCreateDtoIn.getLastname(),
                    investorCreateDtoIn.getUsername(),
                    investorCreateDtoIn.getPassword());

            AccessToken accessToken = securityManager.createInvestorToken(investor);

            InvestorLoginDtoOut returnData = new InvestorLoginDtoOut( investor.getInvestor_id(),investorCreateDtoIn.getFirstname(),investorCreateDtoIn.getLastname(), investorCreateDtoIn.getUsername(),accessToken);
            return ResponseEntity.ok(returnData);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        }
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInvestorByIdCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int investorId)
    {
        checkAnmeldung( accessToken );
        checkBerechtigung( accessToken, investorId );

        try {
            securityManager.removeInvestorToken( accessToken );
            investorService.deleteInvestor(investorId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    //UPDATE
    @PutMapping("{id}")
    public ResponseEntity<InvestorDtoOut> updateInvestorCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int investorId, @RequestBody InvestorUpdateDtoIn investorUpdateDtoIn)
    {
        checkAnmeldung( accessToken );
        checkBerechtigung( accessToken, investorId );

        try {
            return ResponseEntity.ok(
                    new InvestorDtoOut(investorService.updateInvestor(investorId,
                            investorUpdateDtoIn.getNachname(),
                            investorUpdateDtoIn.getVorname(),
                            investorUpdateDtoIn.getPassword()))
            );
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }
    //Portfolios
    // CREATE Portfolios
    @PostMapping("/portfolio")
    public ResponseEntity<PortfolioDtoOut> createPortfolioCon(@RequestHeader AccessToken accessToken, @RequestBody PortfolioDtoIn portfolioDtoIn) {
        checkAnmeldung(accessToken);

        try {
            LocalDateTime now = LocalDateTime.now();

            Portfolio portfolio = portfolioService.createPortfolio(
                    portfolioDtoIn.getInvestorId(),
                    portfolioDtoIn.getBrokerId(),
                    now
            );

            return ResponseEntity.ok(new PortfolioDtoOut(portfolio));

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        }
    }

    // READ Portfolios
    @GetMapping("{investorId}/portfolio")
    public ResponseEntity<Collection<PortfolioDtoOut>> getPortfoliosByInvestor (@RequestHeader AccessToken
                                                                                        accessToken,@PathVariable("investorId") int investorId){
        checkAnmeldung(accessToken);
        try {
            Collection<Portfolio> portfolios = portfolioService.getPortfoliosByInvestorId(investorId);

            return ResponseEntity.ok(
                    portfolios.stream().map(PortfolioDtoOut::new).collect(Collectors.toList())
            );
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }
    // DELETE
    @DeleteMapping("{investorId}/portfolio/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@RequestHeader AccessToken accessToken, @PathVariable("portfolioId") int portfolioId) {
            checkAnmeldung(accessToken);

        try {
            portfolioService.deletePortfolio(portfolioId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    @GetMapping("/brokers/{id}")
    public ResponseEntity<BrokerDtoOut> getBrokerByIdCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int id) {
        checkAnmeldung(accessToken);

        try {
            return ResponseEntity.ok(new BrokerDtoOut(investorService.getBrokerById(id)));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }


    @GetMapping("/brokers")
    public ResponseEntity<Collection<BrokerDtoOut>> getAllBrokerCon(@RequestHeader AccessToken accessToken) {
        checkAnmeldung(accessToken);

        return ResponseEntity.ok(investorService.getAllBrokers().stream()
                .map(BrokerDtoOut::new)
                .toList()
        );
    }

    private void checkAnmeldung(AccessToken accessToken)
    {
        if( !securityManager.hasInvestorAccess(accessToken) )
        {
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Investor nicht angemeldet");
        }
    }

    private void checkBerechtigung(AccessToken accessToken, int investorId )
    {
        if( securityManager.getInvestor(accessToken).getInvestor_id() != investorId )
        {
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Benutzer hat keine Berechtigung");
        }
    }
}
