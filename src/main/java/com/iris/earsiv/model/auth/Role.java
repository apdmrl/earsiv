package com.iris.earsiv.model.auth;

import com.iris.earsiv.model.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * Application Roles are logical grouping of related permissions.
 * <p>
 * ROLE_ADMIN: Süper admin
 * ROLE_SUBEADMIN: Sube Admini, sube ile alakalı işleri ve firmaları inceleyebilecek
 * ROLE_SUBEPERSONAL: Firmaya çalışan ekleyebilecek
 * ROLE_FIRMAPERSONAL: Firmaya dosya ekleyebilecek
 */

@Document(collection = "Role")
@TypeAlias("Role")
@Accessors(chain = true)
public class Role extends Base implements GrantedAuthority {

    @Getter
    @Setter
    private String authority;

    @Getter
    @Setter
    private Set<Permission> permissions;

    public static Role admin() {
        return new Role()
                .setAuthority("ROLE_ADMIN");
    }

    public static Role sube_admin() {
        return new Role()
                .setAuthority("ROLE_SUBEADMIN");
    }

    public static Role sube_personal() {
        return new Role()
                .setAuthority("ROLE_SUBEPERSONAL");
    }

    public static Role firma_personal() {
        return new Role()
                .setAuthority("ROLE_FIRMAPERSONAL");
    }

    @Override
    public int hashCode() {
        return authority.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Role) {
            return getAuthority().equals(((Role) obj).getAuthority());
        } else {
            return false;
        }
    }

}