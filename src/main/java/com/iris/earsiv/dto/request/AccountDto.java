package com.iris.earsiv.dto.request;

import com.iris.earsiv.model.auth.Profile;
import com.iris.earsiv.model.auth.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccountDto {

    private String id;

    private String username;

    private String password;

    private Role role;

    private Profile profile;

    private String subeId;

    private String firmaId;
}
