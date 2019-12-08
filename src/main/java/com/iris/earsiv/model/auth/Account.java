package com.iris.earsiv.model.auth;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iris.earsiv.model.Base;
import com.iris.earsiv.model.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Accessors(chain = true)
@Document(collection = "Account")
@TypeAlias("Account ")
@JsonIgnoreProperties({"password", "accountNonExpired",
        "accountNonLocked", "credentialsNonExpired"})
public class Account extends Base implements UserDetails {

    @Indexed
    @Getter
    @Setter
    @NotNull
    // tc no için kullanılacak. Username TC ye karşılık gelir
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Role role;

    @Getter
    @Setter
    private String firmaId;

    @Getter
    @Setter
    private String subeId;

    @Getter
    @Setter
    private Profile profile;

    @Getter
    @Setter
    private boolean accountNonExpired = true;

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private boolean accountNonLocked = true;

    @Getter
    @Setter
    private boolean credentialsNonExpired = true;

    @Getter
    @Setter
    private Date created;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new Role[]{getRole()});
    }

    @Override
    public boolean isEnabled()  {
        return status == Status.ENABLED;
    }

    public boolean hasRole(Role role) {
        return getAuthorities().contains(role);
    }

    public boolean hasPermission(String target, String action) {
        return role.getPermissions().contains(new Permission()
                .setAction(action)
                .setTarget(target));
    }
}
