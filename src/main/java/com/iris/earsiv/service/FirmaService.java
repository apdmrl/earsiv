package com.iris.earsiv.service;

import com.iris.earsiv.dto.request.AccountDto;
import com.iris.earsiv.dto.request.FirmaDto;
import com.iris.earsiv.dto.response.GenericResponse;
import com.iris.earsiv.model.firma.Firma;
import com.iris.earsiv.model.Sube;
import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.auth.Role;
import com.iris.earsiv.repository.FirmaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FirmaService {

    private Logger logger = LoggerFactory.getLogger(FirmaService.class);

    @Autowired
    private FirmaRepository firmaRepository;

    @Autowired
    private SubeService subeService;

    @Autowired
    private AccountService accountService;


    public List<Firma> list(Account account) {
        if(account.getRole().equals(Role.sube_admin()) || account.getRole().equals(Role.sube_personal())){
            Sube sube = subeService.get(account.getSubeId());
            if(sube != null){
                logger.info("All firmas of sube[" + sube.getName() + "] are listed by sube personali[" + account.getProfile().getFullName() + "]");
                return firmaRepository.findBySubeId(sube.getId());
            } else {
                logger.error("This account does not assign any sube.[account:" + account.getUsername() + "]");
                return null;
            }
        }else {
            // firma personeli firmaları listleyemez
            logger.error("Sadece sube personeli firmaları listeleyebilir");
            return null;
        }
    }

    public Firma get(String id) {
        return firmaRepository.findById(id).orElse(null);
    }

    public List<Account> getAccounts(Account account,Firma firma) {
        if (account.getRole().equals(Role.sube_admin()) || account.getRole().equals(Role.sube_personal())) {
                logger.info("All accounts from " + firma.getProfile().getName() + " are listed by sube personel: " + account.getProfile().getFullName());
                return accountService.findByFirma(firma);
        }
        logger.info("Only sube_admin roles can show accounts");
        return null;
    }

    /* returns GenericResponse
     *   Error Codes:
     *       100: account exists.
     *       200: account does not belong to domain
     *       201: role is not possible.
     *
     *   On Success:
     *       data contains created account
     * */
    public GenericResponse createAccount(Account account, AccountDto accountDto) {
        if (accountDto.getId() != null) {
            Account acct = accountService.get(account.getId());
            if (acct != null) {
                acct.setRole(accountDto.getRole());
                acct.setProfile(accountDto.getProfile());
                acct = accountService.save(acct);

                return new GenericResponse()
                        .setCode(1)
                        .setData(acct);
            } else {
                return new GenericResponse()
                        .setCode(100);
            }
        } else {
            if (!accountService.existByTC(accountDto.getUsername())) {
                if (accountDto.getRole().equals(Role.firma_personal())) {
                    Account newAcc = accountService.createAccount(accountDto);

                    logger.info("Account create request success. [username: " + accountDto.getUsername() + " fullname:" + accountDto.getProfile().getFullName() + "]");
                    return new GenericResponse()
                            .setCode(0)
                            .setData(newAcc);
                } else {
                    logger.error("Account create request failed. Reason Role unallowed. [role: " + accountDto.getRole().getAuthority() + "]");
                    return new GenericResponse()
                            .setCode(201);
                }
            } else {
                logger.error("Account create request failed. Reason: Account exists. [username: " + accountDto.getUsername() + "]");
                return new GenericResponse()
                        .setCode(100);
            }
        }
    }

    public GenericResponse removeAccount(Account account, String id) {
        accountService.removeAccount(account, id);

        logger.info("Account deletion success. [AccountId: " + id + "]");
        return new GenericResponse()
                .setCode(0);
    }

    /*
     * returns GenericResponse
     *  sadece süper_admin ve şube_admini ekleyebilir
     *  ErrorCodes:
     *      100: firma exists
     *      200: account does not belong to domain
     *
     *  On Success:
     *      data contains created sube
     * */
    public GenericResponse createFirma(FirmaDto firmaDto) {
        if (firmaDto.getId() == null) {// new Firma
            //create Firma
            Firma firma = new Firma()
                    .setProfile(firmaDto.getProfile());

            firma.setId(UUID.randomUUID().toString());
            firma = firmaRepository.save(firma);
            logger.info("Firma created request success. [Firma: " + firmaDto.getProfile().getName() + " isveren:" + firmaDto.getProfile().getIsveren().getFullname() + "]");
            return new GenericResponse()
                    .setCode(0)
                    .setData(firma);
        } else {
            Firma firma = get(firmaDto.getId());
            firma.setProfile(firmaDto.getProfile());
            firma.setEnabled(firmaDto.isEnabled());
            firma = firmaRepository.save(firma);

            logger.info("Firma update request success. [firma:" + firma.getProfile().getName() + "]");
            return new GenericResponse()
                    .setCode(1)
                    .setData(firma);
        }
    }
}
