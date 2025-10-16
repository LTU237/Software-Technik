package com.example.portfolio.api.security;

import com.example.portfolio.model.Investor;
import org.springframework.stereotype.Service;

import com.example.portfolio.model.Broker;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecurityManager
{
    private Map<AccessToken, Broker> accessList = new ConcurrentHashMap<>();
    private Map<AccessToken, Investor> accessListIn = new ConcurrentHashMap<>();

    //Broker
    public AccessToken createToken(Broker broker)
    {
        String uuid = UUID.randomUUID().toString();
        AccessToken accessToken = new AccessToken(uuid);

        accessList.put(accessToken, broker);

        return accessToken;
    }

    public boolean removeToken(AccessToken accessToken)
    {
        return accessList.remove(accessToken) != null;
    }

   public boolean hasAccess(AccessToken accessToken)
    {
        return accessList.containsKey(accessToken);
    }

    public Broker getBroker(AccessToken accessToken)
    {
        return accessList.get(accessToken);
    }

    //Investor

    public AccessToken createInvestorToken(Investor investor)
    {
        String uuid = UUID.randomUUID().toString();
        AccessToken accessToken = new AccessToken(uuid);

        accessListIn.put(accessToken, investor);

        return accessToken;
    }

    public boolean removeInvestorToken(AccessToken accessToken)
    {
        return accessListIn.remove(accessToken) != null;
    }


    public boolean hasInvestorAccess(AccessToken accessToken)
    {
        return accessListIn.containsKey(accessToken);
    }

    public Investor getInvestor(AccessToken accessToken)
    {
        return accessListIn.get(accessToken);
    }
}
