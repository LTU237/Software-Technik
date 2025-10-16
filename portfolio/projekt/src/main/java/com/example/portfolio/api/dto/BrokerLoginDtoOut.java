package com.example.portfolio.api.dto;

import com.example.portfolio.api.security.AccessToken;

public class BrokerLoginDtoOut {

    private final int id;
    private final String benutzername;
    private final String company;
    private final AccessToken credential;

    public BrokerLoginDtoOut(int id, String benutzername, String company, AccessToken accessToken)
    {
        this.id = id;
        this.benutzername = benutzername;
        this.company=company;
        this.credential = accessToken;
    }

    public int getId()
    {
        return id;
    }

    public String getBenutzername()
    {
        return benutzername;
    }

    public String getCompany() {return company;}

    public AccessToken getCredential()
    {
        return credential;
    }
}
