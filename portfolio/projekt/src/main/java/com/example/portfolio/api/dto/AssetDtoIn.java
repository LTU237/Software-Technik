package com.example.portfolio.api.dto;

import com.example.portfolio.model.EnumAsset;

public class AssetDtoIn
{
    private String name;
    private EnumAsset kind;

    public AssetDtoIn() {}

    public EnumAsset getKind() {
        return kind;
    }

    public void setKind(EnumAsset kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
