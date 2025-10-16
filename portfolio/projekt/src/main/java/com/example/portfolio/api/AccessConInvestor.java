package com.example.portfolio.api;

import com.example.portfolio.api.dto.InvestorLoginDtoOut;
import com.example.portfolio.api.dto.LoginDtoIn;
import com.example.portfolio.api.dto.util.PasswordTools;
import com.example.portfolio.api.security.AccessToken;
import com.example.portfolio.api.security.SecurityManager;
import com.example.portfolio.model.Broker;
import com.example.portfolio.model.Investor;
import com.example.portfolio.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("accessInvestor")
public class AccessConInvestor {
    private final SecurityManager securityManager;
    private final InvestorService investorService;

    @Autowired
    public AccessConInvestor(SecurityManager securityManager, InvestorService investorService)
    {
        this.securityManager = securityManager;
        this.investorService = investorService;
    }

    @PostMapping
    public ResponseEntity<InvestorLoginDtoOut> login(@RequestBody LoginDtoIn loginDtoIn)
    {
        String username = loginDtoIn.getUsername();
        String password = loginDtoIn.getPassword();
        System.out.println("Attempting login for username: " + username);

        Investor investor = investorService.getInvestorByUsername(username);

        if ( !PasswordTools.checkPassword(password, investor.getPassword_hash(), investor.getPassword_salt()) ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AccessToken accessToken = securityManager.createInvestorToken(investor);
        InvestorLoginDtoOut returnData = new InvestorLoginDtoOut( investor.getInvestor_id(), investor.getFirstname(), investor.getLastname(), investor.getUsername(), accessToken);

        return ResponseEntity.ok(returnData);
    }


    @DeleteMapping
    public ResponseEntity<Boolean> logout(@RequestHeader AccessToken accessToken)
    {
        return ResponseEntity.ok(securityManager.removeInvestorToken( accessToken ));
    }
}
