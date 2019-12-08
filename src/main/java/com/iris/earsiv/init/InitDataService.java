package com.iris.earsiv.init;

import com.iris.earsiv.model.Status;
import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.auth.Role;
import com.iris.earsiv.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InitDataService {
    private Logger logger = LoggerFactory.getLogger(InitDataService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    /**
     * Uygulama Başladığında Varsayılan Olarak Oluşturulan Veriler.
     */
    public void init() {

        String adminName = environment.getProperty("app.init.admin");

        Account admin = accountRepository.findByUsername(adminName);

        if (admin == null) {
            admin = new Account()
                    .setUsername(adminName)
                    .setPassword(passwordEncoder.encode("12345678"))
                    .setRole(Role.admin())
                    .setStatus(Status.ENABLED);
            admin.setId(UUID.randomUUID().toString());
            accountRepository.save(admin);
            logger.info("Initial admin not found; created one.");

        }
    }
}
