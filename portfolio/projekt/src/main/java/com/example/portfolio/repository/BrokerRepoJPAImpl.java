package com.example.portfolio.repository;

import com.example.portfolio.api.dto.util.PasswordTools;
import com.example.portfolio.model.Broker;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class BrokerRepoJPAImpl implements BrokerRepoJPA{
    EntityManager entityManager;

    @Autowired
    public BrokerRepoJPAImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Broker createBroker(String username, String company, String password) {
        byte[] passwordSalt = PasswordTools.generateSalt();
        byte[] passwordHash = PasswordTools.generatePasswordHash(password, passwordSalt);

        Broker broker = new Broker(username, company, passwordHash, passwordSalt);

        entityManager.persist(broker);
        entityManager.flush();
        entityManager.refresh(broker);

        return broker;
    }

    @Override
    public Broker update(int id, String company, String password) {
        Broker broker = entityManager.find(Broker.class, id);
        byte[] pwd_salt = broker.getPasswordSalt();
        byte[] pwd_hash = PasswordTools.generatePasswordHash(password, pwd_salt);

        broker.setCompany( company );
        broker.setPasswordHash( pwd_hash );

        entityManager.merge(broker);
        entityManager.flush();
        entityManager.refresh(broker);

        return broker;
    }

    @Override
    public Broker mergeAndRefresh(Broker broker) {
        entityManager.persist(broker);
        entityManager.flush();
        entityManager.refresh(broker);

        return broker;
    }

    @Override
    public void delete(int id) {
        Broker broker = entityManager.find(Broker.class, id);
        entityManager.remove(broker);
        return;
    }
}
