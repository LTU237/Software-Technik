package com.example.portfolio.repository;

import com.example.portfolio.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerRepo extends JpaRepository<Broker, Integer>, BrokerRepoJPA {

    @Query("select b from Broker b")
    List<Broker> getAll();

    @Query("select b from Broker b where b.username = ?1")
    Broker getBrokerByUsername(String username);

    @Query("select b from Broker b where b.broker_id = ?1")
    Broker getById(int id);
   // Broker findByUsernameAndPassword(String username, String password);
}
