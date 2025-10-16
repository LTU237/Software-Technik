package com.example.portfolio.api;

import com.example.portfolio.api.dto.BrokerLoginDtoOut;
import com.example.portfolio.api.dto.LoginDtoIn;
import com.example.portfolio.api.dto.util.PasswordTools;
import com.example.portfolio.api.security.AccessToken;
import com.example.portfolio.api.security.SecurityManager;
import com.example.portfolio.model.Broker;
import com.example.portfolio.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("accessbroker")
public class AccessConBroker {
    private final SecurityManager securityManager;
    private final BrokerService brokerService;

    @Autowired
    public AccessConBroker(SecurityManager securityManager, BrokerService brokerService)
    {
        this.securityManager = securityManager;
        this.brokerService = brokerService;
    }

    @PostMapping
    public ResponseEntity<BrokerLoginDtoOut> login(@RequestBody LoginDtoIn loginDtoIn)
    {
        String username = loginDtoIn.getUsername();
        String password = loginDtoIn.getPassword();
        System.out.println("Attempting login for username: " + username);

        Broker broker = brokerService.getBrokerByUsername(username);

        if ( !PasswordTools.checkPassword(password, broker.getPasswordHash(),broker.getPasswordSalt()) ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AccessToken accessToken = securityManager.createToken(broker);
        BrokerLoginDtoOut returnData = new BrokerLoginDtoOut( broker.getBroker_id(), broker.getUsername(),broker.getCompany(), accessToken);

        return ResponseEntity.ok(returnData);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> logout(@RequestHeader AccessToken accessToken)
    {
        return ResponseEntity.ok(securityManager.removeToken( accessToken ));
    }
}
