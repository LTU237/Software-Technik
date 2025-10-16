package com.example.portfolio.service;

import com.example.portfolio.model.*;
import com.example.portfolio.repository.AssetRepo;
import com.example.portfolio.repository.BrokerRepo;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.portfolio.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class BrokerService {


    private final AssetRepo assetRepo;
    private BrokerRepo brokerRepository;

    @Autowired
    public BrokerService(BrokerRepo brokerRepository, AssetRepo assetRepo){
        this.brokerRepository=brokerRepository;
        this.assetRepo = assetRepo;
    }

    public List<Broker> getAllBrokers() {
        return brokerRepository.getAll();
    }

    public Broker getBrokerByUsername(String username) {
        return brokerRepository.getBrokerByUsername(username);
    }

    public Broker getBrokerById(int id) {
        return brokerRepository.getById(id);
    }

    public Broker createBroker(String username, String company, String password) {
        return brokerRepository.createBroker(username, company, password);
    }

    public Broker updateBroker(int id, String company, String password) {
        return brokerRepository.update(id, company, password);
    }

    public void deleteBroker(int id) {
        brokerRepository.delete(id);
    }

    public Asset addAsset(Broker broker, EnumAsset kind, String name ){
        Asset asset = new Asset(broker, kind, name);
        broker.addAssets(asset);

        brokerRepository.mergeAndRefresh(broker);

        return asset;
    }

    public void deleteAsset(Broker broker, int assetId) {
        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new NotFoundException("Asset not found"));
        assetRepo.delete(asset);

    }

}