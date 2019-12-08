package com.iris.earsiv.model.firma;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    @Getter
    @Setter
    private String isverenAdi;

    @Getter
    @Setter
    private String isverenSoyadi;

    @Getter
    @Setter
    private String isverenEmail;

    @Getter
    @Setter
    private String isverenGsm;

    public String getFullname() {
        return isverenAdi + " " + isverenSoyadi;
    }
}
