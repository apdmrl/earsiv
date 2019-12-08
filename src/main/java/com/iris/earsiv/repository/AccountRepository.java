package com.iris.earsiv.repository;


import com.iris.earsiv.model.firma.Firma;
import com.iris.earsiv.model.Sube;
import com.iris.earsiv.model.auth.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUsername(String username);

    List<Account> findBySube(Sube sube);

    List<Account> findByFirma(Firma firma);
}
