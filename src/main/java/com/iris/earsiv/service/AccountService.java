package com.iris.earsiv.service;

import com.iris.earsiv.dto.request.AccountDto;
import com.iris.earsiv.model.firma.Firma;
import com.iris.earsiv.model.Status;
import com.iris.earsiv.model.Sube;
import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.auth.Role;
import com.iris.earsiv.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * username = TC
     * Her türlü account çekilir firması olan/olmayan, şubesi olan/olmayan
     */
    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            logger.error("Account with username not found. [TC No: " + username + " ]");
            throw new UsernameNotFoundException(username);
        } else {
            logger.info("User logged in. [TC No:" + username + " ]");
            account.setRole(roleService.get(account.getRole().getAuthority()));
            return account;
        }
    }

    public boolean authenticateAs(String username) {
        try {
            Account account = loadUserByUsername(username);
            setAuthenticationPrincipal(account);
            logger.info("Successfully authenticated as user: [ Username: " + username +" ]");
            return true;
        } catch (UsernameNotFoundException exc) {
            logger.error("Tried to authenticate with an unknown user: [ Username: " + username + " ]");
            return false;
        }
    }

    public void setAuthenticationPrincipal(Account account) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(account, account.getPassword(), account.getAuthorities())
        );
    }

    public void setPassword(Account account, String password) {
        Account acct = accountRepository.findByUsername(account.getUsername());

        if(acct != null) {
            acct.setPassword(passwordEncoder.encode(password));
            acct.setStatus(Status.ENABLED);
            accountRepository.save(acct);
            logger.info("Account password is set. [Account = " + acct.getUsername() + "]");
        }
    }

    //sadece admin tarafından kullanılır
    public List<Account> findAllAccount(Account account) {
        if(account.getRole().equals(Role.admin())){
            return accountRepository.findAll();
        }else {
            logger.error("find All Account methodu yalnızca admin tarafından kullanılabilir");
            return null;
        }
    }

    public List<Account> findBySube(Sube sube) {
        return accountRepository.findBySube(sube);
    }

    public List<Account> findByFirma(Firma firma) {
        return accountRepository.findByFirma(firma);
    }

    public Account get(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public  boolean existByTC(String username) {
        return accountRepository.exists(Example.of(new Account().setUsername(username)));
    }


    public Account createAccount(AccountDto accountDto) {
        Account account = new Account()
                .setUsername(accountDto.getUsername())
                .setPassword(passwordEncoder.encode(accountDto.getPassword()))
                .setSubeId(accountDto.getSubeId())
                .setFirmaId(accountDto.getFirmaId())
                .setCreated(new Date())
                .setRole(accountDto.getRole())
                .setProfile(accountDto.getProfile());

        account.setId(UUID.randomUUID().toString());
        account = accountRepository.save(account);

        return account;
    }

    public void removeAccount(Account account, String id){
        Account deletedAccount = accountRepository.findById(id).orElse(null);
        if(deletedAccount != null){
            accountRepository.delete(deletedAccount);
            logger.info("Account deleted successfully by: " + account.getProfile().getFullName());
        }
    }

    public Account save(Account acct) {
        return accountRepository.save(acct);
    }

}
