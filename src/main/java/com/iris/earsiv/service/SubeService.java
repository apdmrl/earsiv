package com.iris.earsiv.service;

import com.iris.earsiv.dto.request.AccountDto;
import com.iris.earsiv.dto.request.SubeDto;
import com.iris.earsiv.dto.response.GenericResponse;
import com.iris.earsiv.model.Base;
import com.iris.earsiv.model.firma.Firma;
import com.iris.earsiv.model.Status;
import com.iris.earsiv.model.Sube;
import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.auth.Role;
import com.iris.earsiv.repository.SubeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class SubeService {

    private Logger logger = LoggerFactory.getLogger(SubeService.class);

    private Role[] allowedRoles = new Role[]{Role.sube_admin(), Role.sube_personal()};

    @Autowired
    private AccountService accountService;

    @Autowired
    private FirmaService firmaService;

    @Autowired
    private SubeRepository subeRepository;


    public List<Sube> list() {
        logger.info("Sube list requested.");
        return subeRepository.findAll(new Sort(Sort.Direction.DESC, "updated"));
    }

    public Sube get(String id) {
        return subeRepository.findById(id).orElse(null);
    }

    public List<Account> getAccounts(Account account) {
        if (account.getRole().equals(Role.sube_admin())) {
            Sube sube = get(account.getSubeId());
            if (sube != null) {
                logger.info("All accounts from " + sube.getName() + " are listed by sube admin: " + account.getProfile().getFullName());
//                return accountService.findBySube(sube);
            } else {
                logger.error("Sube not found. Requested subeID:" + account.getSubeId());
                return null;
            }
        }
        logger.info("Only sube_admin roles can show accounts");
        return null;
    }

    public List<Firma> listFirmas(Account account) {
        return firmaService.list(account);
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
                if (Arrays.asList(allowedRoles).contains(accountDto.getRole())) {
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
     *  sadece süper_admin ekleyebilir
     *  ErrorCodes:
     *      100: sube exists
     *      200: account role doesn't allowed
     *
     *  On Success:
     *      data contains created sube
     * */
    public GenericResponse createSube(SubeDto subeDto) {
        if (subeDto.getId() == null) {// new sube
            if (!subeDto.getAccount().getRole().equals(Role.sube_admin())) {
                logger.error("Sube created request failed because role not allowed");
                return new GenericResponse()
                        .setCode(200);
            }
            //create Sube
            Sube sube = new Sube()
                    .setName(subeDto.getName())
                    .setAddress(subeDto.getAddress())
                    .setStatus(Status.ENABLED);
            sube.setId(UUID.randomUUID().toString());

            //create Sube Admini

            AccountDto accountDto = subeDto.getAccount();
            accountDto.setRole(Role.sube_admin());
            Account acc = accountService.createAccount(accountDto);
            //add subeAdmin to subeCalisanlari
            sube.setSubeAdmini(acc);


            sube = subeRepository.save(sube);
            logger.info("Sube created request success. [sube: " + subeDto.getName() + "; sube admin:" + accountDto.getUsername() + "]");
            return new GenericResponse()
                    .setCode(0)
                    .setData(sube);
        } else {
            Sube sube = get(subeDto.getId());
            sube.setName(subeDto.getName());
            sube.setAddress(subeDto.getAddress());
            sube.setEnabled(subeDto.isEnabled());
            sube = subeRepository.save(sube);

            logger.info("Sube update request success. [sube:" + sube.getName() + "]");
            return new GenericResponse()
                    .setCode(1)
                    .setData(sube);
        }
    }

    //TODO: Daha bitmedi firmanın dosyalarınında silinmesi gerekiyor.
    public void removeSube(Account account, String id) {
        Sube sube = get(id);

        if (sube != null) {

            List<Base> related = new ArrayList<>();
//            related.addAll(accountService.findBySube(sube));

            //Firmanın dosyaları da silinecek
        }
    }
}
