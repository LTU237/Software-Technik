package com.example.portfolio.repository;

import com.example.portfolio.model.Asset;
import com.example.portfolio.model.Broker;
import com.example.portfolio.model.EnumAsset;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class AssetRepoJPAImpl implements AssetRepoJPA{
    /*EntityManager entityManager;

    @Autowired
    public AssetRepoJPAImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public Asset createAsset(Broker broker, String name, EnumAsset kind) {

        Asset asset = new Asset(broker, kind, name);

        entityManager.persist(asset);
        entityManager.flush();
        entityManager.refresh(asset);

        return asset;
    }

    @Override
    public Asset update(int id, String name, EnumAsset kind) {
        Asset asset = entityManager.find(Asset.class, id);

        asset.setName( name );
        asset.setKind( kind );

        entityManager.merge(asset);
        entityManager.flush();
        entityManager.refresh(asset);

        return asset;
    }

    @Override
    public Asset mergeAndRefresh(Asset asset) {
        entityManager.persist(asset);
        entityManager.flush();
        entityManager.refresh(asset);

        return asset;
    }

    @Override
    public void delete(int id) {
        Asset asset = entityManager.find(Asset.class, id);
        entityManager.remove(asset);
        return;
    }*/
}
