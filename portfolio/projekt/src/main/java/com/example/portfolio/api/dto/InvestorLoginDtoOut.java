package com.example.portfolio.api.dto;

import com.example.portfolio.api.security.AccessToken;

public class InvestorLoginDtoOut {

    private final int id;
    private final String firstname;
    private final String lastname;
    private final String benutzername;
    private final AccessToken credential;

    public InvestorLoginDtoOut(int id, String firstname, String lastname, String benutzername, AccessToken accessToken)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.benutzername = benutzername;
        this.credential = accessToken;
    }

    public int getId()
    {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBenutzername()
    {
        return benutzername;
    }

    public AccessToken getCredential()
    {
        return credential;
    }
}
