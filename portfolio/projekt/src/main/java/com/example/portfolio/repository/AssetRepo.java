package com.example.portfolio.repository;

import com.example.portfolio.model.Asset;
import com.example.portfolio.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Integer>, AssetRepoJPA {
    @Query("select a from Asset a")
    List<Asset> getAll();

    @Query("select a from Asset a where a.name = ?1")
    Asset getAssetByName(String name);

    @Query("select a from Asset a where a.id = ?1")
    Asset getAssetById(int id);
}
