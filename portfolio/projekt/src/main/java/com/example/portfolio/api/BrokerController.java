package com.example.portfolio.api;


import com.example.portfolio.api.dto.*;
import com.example.portfolio.api.dto.BrokerLoginDtoOut;
import com.example.portfolio.api.security.AccessToken;
import com.example.portfolio.api.security.SecurityManager;
import com.example.portfolio.model.Broker;
import com.example.portfolio.model.Asset;
import com.example.portfolio.model.exception.NotFoundException;
import com.example.portfolio.repository.BrokerRepo;
import com.example.portfolio.service.BrokerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;


@CrossOrigin
@RestController
@RequestMapping("/brokers")
public class BrokerController {

    private final BrokerService brokerService;
    private final SecurityManager securityManager;
    private final BrokerRepo brokerRepo;


    public BrokerController(BrokerService brokerService, SecurityManager securityManager, BrokerRepo brokerRepo) {
        this.brokerService = brokerService;
        this.securityManager = securityManager;
        this.brokerRepo = brokerRepo;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<BrokerLoginDtoOut> createBrokerCon(@RequestBody BrokerCreateDtoIn brokerCreateDtoIn) {
        try {
            Broker broker = brokerService.createBroker(brokerCreateDtoIn.getUsername(),
                    brokerCreateDtoIn.getCompany(),
                    brokerCreateDtoIn.getPassword());

            AccessToken accessToken = securityManager.createToken(broker);

            BrokerLoginDtoOut returnData = new BrokerLoginDtoOut(broker.getBroker_id(), brokerCreateDtoIn.getUsername(), brokerCreateDtoIn.getCompany(), accessToken);
            return ResponseEntity.ok(returnData);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        }
    }

    //READAll
    @GetMapping
    public ResponseEntity<Collection<BrokerDtoOut>> getAllBrokerCon(@RequestHeader AccessToken accessToken) {
        checkAnmeldung(accessToken);

        return ResponseEntity.ok(brokerService.getAllBrokers().stream()
                .map(BrokerDtoOut::new)
                .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<BrokerDtoOut> getBrokerByIdCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int id) {
        checkAnmeldung(accessToken);

        try {
            return ResponseEntity.ok(new BrokerDtoOut(brokerService.getBrokerById(id)));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBrokerByIdCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int brokerId) {
        checkAnmeldung(accessToken);
        checkBerechtigung(accessToken, brokerId);

        try {
            securityManager.removeToken(accessToken);
            brokerService.deleteBroker(brokerId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }


    //UPDATE
    @PutMapping("{id}")
    public ResponseEntity<BrokerDtoOut> updateBrokerCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int brokerId, @RequestBody BrokerUpdateDtoIn brokerUpdateDtoIn) {
        checkAnmeldung(accessToken);
        checkBerechtigung(accessToken, brokerId);

        try {
            return ResponseEntity.ok(
                    new BrokerDtoOut(brokerService.updateBroker(brokerId,
                            brokerUpdateDtoIn.getCompany(),
                            brokerUpdateDtoIn.getPassword()))
            );
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    //Assets Verwaltung
    //READAssets
    @GetMapping("{id}/asset")
    public ResponseEntity<Collection<AssetDtoOut>> getAssetsCon(@RequestHeader AccessToken accessToken, @PathVariable("id") int brokerId) {
        checkAnmeldung(accessToken);
        checkBerechtigung(accessToken, brokerId);

        try {
            Broker broker = brokerService.getBrokerById(brokerId);

            return ResponseEntity.ok(
                    broker.getAssets().stream()
                            .map(AssetDtoOut::new)
                            .toList()
            );
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    //create Assets
    @PostMapping("{id}/asset")
    public ResponseEntity<AssetDtoOut> createAsset(@RequestHeader AccessToken accessToken, @PathVariable("id") int brokerId, @RequestBody AssetDtoIn assetDtoIn) {
        checkAnmeldung(accessToken);
        checkBerechtigung(accessToken, brokerId);

        try {
            Broker broker = brokerService.getBrokerById(brokerId);
            Asset asset = brokerService.addAsset(broker,
                    assetDtoIn.getKind(),
                    assetDtoIn.getName());

            return ResponseEntity.ok(new AssetDtoOut(asset));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        }
    }

    //delete Assets
    @DeleteMapping("{brokerid}/asset/{Assetid}")
    public ResponseEntity<Void> deleteAsset(@RequestHeader AccessToken accessToken,@PathVariable("brokerid") int brokerId , @PathVariable("Assetid") int assetId) {
        checkAnmeldung(accessToken);
        checkBerechtigung( accessToken, brokerId );

        try {
            Broker broker = brokerService.getBrokerById(brokerId);
            brokerService.deleteAsset(broker, assetId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
        } catch (IllegalStateException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        }
    }

    private void checkAnmeldung (AccessToken accessToken)
    {
        if (!securityManager.hasAccess(accessToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Broker nicht angemeldet");
        }
    }

    private void checkBerechtigung (AccessToken accessToken,int brokerId )
    {
        if (securityManager.getBroker(accessToken).getBroker_id() != brokerId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Broker hat keine Berechtigung");
        }
    }

}
