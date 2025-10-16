package com.example.portfolio.api.dto;

import com.example.portfolio.model.Asset;
import com.example.portfolio.model.EnumAsset;

public class AssetDtoOut
{
    int id;
    private final String name;
    private final EnumAsset kind;

    public AssetDtoOut(Asset asset)
    {
        this.id=asset.getAssetId();
        this.name = asset.getName();
        this.kind = asset.getKind();
    }

    public int getId() {
        return id;
    }

    public EnumAsset getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }
}
