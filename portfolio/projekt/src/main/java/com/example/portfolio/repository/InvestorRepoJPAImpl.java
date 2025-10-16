package com.example.portfolio.repository;

import com.example.portfolio.api.dto.util.PasswordTools;
import com.example.portfolio.model.Investor;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestorRepoJPAImpl implements InvestorRepoJPA{
    EntityManager entityManager;

    @Autowired
    public InvestorRepoJPAImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Investor createInvestor(String firstname, String lastname, String username,String password) {
        byte[] passwordSalt = PasswordTools.generateSalt();
        byte[] passwordHash = PasswordTools.generatePasswordHash(password, passwordSalt);

        Investor investor = new Investor(firstname, lastname, username, passwordHash, passwordSalt);

        entityManager.persist(investor);
        entityManager.flush();
        entityManager.refresh(investor);

        return investor;
    }

    @Override
    public Investor update(int id, String firstname, String lastname, String password) {
        Investor investor = entityManager.find(Investor.class, id);
        byte[] pwd_salt = investor.getPassword_salt();
        byte[] pwd_hash = PasswordTools.generatePasswordHash(password, pwd_salt);

        investor.setFirstname( firstname );
        investor.setLastname( lastname );
        investor.setPassword_hash( pwd_hash );

        entityManager.merge(investor);
        entityManager.flush();
        entityManager.refresh(investor);

        return investor;
    }

    @Override
    public Investor mergeAndRefresh(Investor investor) {
        entityManager.persist(investor);
        entityManager.flush();
        entityManager.refresh(investor);

        return investor;
    }

    @Override
    public void delete(int id) {
        Investor investor = entityManager.find(Investor.class, id);
        entityManager.remove(investor);
        return;

    }
}
