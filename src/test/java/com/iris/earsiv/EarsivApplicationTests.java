package com.iris.earsiv;

import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.firma.Firma;
import com.iris.earsiv.model.firma.FirmaProfile;
import com.iris.earsiv.model.testmodels.*;
import com.iris.earsiv.repository.AccountRepository;
import com.iris.earsiv.repository.FirmaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class EarsivApplicationTests {

    @Autowired
    private firm1Repository firm1Repository;
    @Autowired
    private firm2Repository firm2Repository;
    @Autowired
    private testClassRepository testClassRepository;
    @Autowired
    private FirmaRepository firmaRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testDbreff() {
        Account acc1 = new Account()
                .setUsername("ddd");
        acc1.setId(UUID.randomUUID().toString());
        Account acc2 = new Account()
                .setUsername("aaa");
        acc2.setId(UUID.randomUUID().toString());
        accountRepository.save(acc1);
        accountRepository.save(acc2);

        FirmaProfile fp = new FirmaProfile()
                .setName("firma1");

        List<Account> accounts = new ArrayList<Account>();
        accounts.add(acc1);
        Firma firma2 = new Firma()
                .setProfile(fp)
                .setSubeId("1")
                .setIsgUzmanlari(accounts);
        firma2.setId(UUID.randomUUID().toString());
        accounts.add(acc2);
        Firma firma = new Firma()
                .setProfile(fp)
                .setSubeId("1")
                .setIsgUzmanlari(accounts);
        firma.setId(UUID.randomUUID().toString());
        firmaRepository.save(firma);
        firmaRepository.save(firma2);


        List<Firma> firmas = firmaRepository.findBySubeIdAndFirmaCalisanlariContains("1",acc1);

    }
}
