package com.iris.earsiv.model.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
public class Profile {
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String gsm;

    @Getter
    @Setter
    private String email;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
