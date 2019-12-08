package com.iris.earsiv.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iris.earsiv.model.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubeDto {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Address address;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    private AccountDto account;
}
