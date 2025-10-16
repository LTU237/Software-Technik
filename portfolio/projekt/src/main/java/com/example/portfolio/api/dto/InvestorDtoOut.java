package com.example.portfolio.api.dto;

import com.example.portfolio.model.Investor;
import lombok.Getter;

@Getter
public class InvestorDtoOut
{
    int id;
    String firstname;
    String lastname;
    String username;

    public InvestorDtoOut(Investor person)
    {
        this.id = person.getInvestor_id();
        this.firstname = person.getFirstname();
        this.lastname = person.getLastname();
        this.username = person.getUsername();
    }

}
