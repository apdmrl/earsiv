package com.iris.earsiv.init;

import com.iris.earsiv.model.auth.Permission;
import com.iris.earsiv.model.auth.Role;
import com.iris.earsiv.model.system.InfoKey;
import com.iris.earsiv.model.system.SystemInfo;
import com.iris.earsiv.repository.RoleRepository;
import com.iris.earsiv.service.RoleService;
import com.iris.earsiv.service.SystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class InitSecurityService {

    private Logger logger = LoggerFactory.getLogger(InitSecurityService.class);

    @Value("classpath:/static/app/files/init-roles")
    public Resource rolesFile;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SystemInfoService systemInfoService;

    @Autowired
    private RoleService roleService;

    public void init() {
        initRolesAndPermissions();
    }

    private void initRolesAndPermissions() {

        String latest = "";
        SystemInfo roleInfo = systemInfoService.get(InfoKey.ROLE_INIT);

        if (roleInfo == null || roleInfo.getValue() == null || roleInfo.getValue().equals("")) {
            roleInfo = new SystemInfo()
                    .setKey(InfoKey.ROLE_INIT)
                    .setValue("0");
            roleInfo.setId(UUID.randomUUID().toString());
        }
        try {
            switch (roleInfo.getValue()) {
                case "":
                case "0":
                    latest = "0";
                    readInitialRoles();
                    latest = "1";
                    logger.info("Role version moved null to 1");
                case "1":
                    latest = "1";
                    role1to2();
                    logger.info("Role version moved 1 to 2");
                    latest = "2";
                case "2":
                    logger.info("Role version already in latest version");
                    latest = "2";
                    break;
                default:
                    logger.error("Role version from database has invalid value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            roleInfo.setValue(latest);
            systemInfoService.save(roleInfo);
        }
    }

    private void role1to2() {
        List<Role> roleList = roleService.list();
        List<String> roleNameList = roleService.listNames();

        List<String> roleToAddPermission = Arrays.asList("ROLE_ADMIN", "ROLE_SUBEADMIN", "ROLE_SUBEPERSONAL","ROLE_FIRMAPERSONAL");
        for (int i = (roleList.size() - 1); i >= 0; i--) {

            if (roleNameList.contains(roleList.get(i).getAuthority()) && roleList.get(i).getPermissions() != null) {
                Set permissions = roleList.get(i).getPermissions();
                Permission perTestAdd = new Permission()
                        .setTarget("simulation")
                        .setAction("test-add");
                Permission perTestDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("test-delete");
                Permission perVectorAdd = new Permission()
                        .setTarget("simulation")
                        .setAction("vector-add");
                Permission perVectorDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("vector-delete");
                List<Permission> removedList = new ArrayList<>();
                removedList.add(perTestAdd);
                removedList.add(perTestDelete);
                removedList.add(perVectorAdd);
                removedList.add(perVectorDelete);

                permissions.removeAll(removedList);

                Permission perTestStartDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("test-start-delete");
                Permission perVectorAddDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("vector-add-delete");
                Permission perProfileAddDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("profile-add-edit-delete");
                Permission perPolicyAddDelete = new Permission()
                        .setTarget("simulation")
                        .setAction("policy-add-edit-delete");
                List<Permission> addList = new ArrayList<>();
                addList.add(perTestStartDelete);
                addList.add(perVectorAddDelete);
                addList.add(perProfileAddDelete);
                addList.add(perPolicyAddDelete);

                if (roleToAddPermission.contains(roleList.get(i).getAuthority())) {
                    permissions.addAll(addList);
                }

                if (roleList.get(i).getAuthority().equals("ROLE_ADMIN")) {
                    Permission perView = new Permission()
                            .setTarget("simulation")
                            .setAction("view");
                    permissions.add(perView);
                    permissions.add(perVectorAddDelete);
                }
            } else {
                roleList.remove(i);
            }
        }
        roleService.saveAll(roleList);
    }

    public void readInitialRoles() {

        Map<String, Role> roles = new HashMap<>();
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(rolesFile.getInputStream()));

            List<String> initialRoles = new ArrayList<>();
            String ln;
            while ((ln = reader.readLine()) != null) {
                initialRoles.add(ln);
            }

            initialRoles.forEach(line -> {
                if (line != null && line.length() > 0) {
                    String[] lineParts = line.split(",");

                    Role role;
                    if (roles.containsKey(lineParts[0])) {
                        role = roles.get(lineParts[0]);
                    } else {
                        role = new Role()
                                .setAuthority(lineParts[0]);
                        role.setId(UUID.randomUUID().toString());
                        role.setPermissions(new HashSet<>());
                        roles.put(role.getAuthority(), role);
                    }

                    for (int i = 2; i < lineParts.length; i++) {
                        role.getPermissions()
                                .add(new Permission()
                                        .setTarget(lineParts[1])
                                        .setAction(lineParts[i]));
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        roleService.saveAll(new ArrayList<>(roles.values()));
    }
}
