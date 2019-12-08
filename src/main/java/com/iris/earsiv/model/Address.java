package com.iris.earsiv.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Address {

    @Getter
    @Setter
    private String line;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;
}
