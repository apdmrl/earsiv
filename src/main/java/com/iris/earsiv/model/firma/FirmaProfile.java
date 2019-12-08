package com.iris.earsiv.model.firma;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iris.earsiv.model.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmaProfile {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String workPlaceNickName;

    @Getter
    @Setter
    private Address address;

    @Getter
    @Setter
    private Address workplaceAddress;

    @Getter
    @Setter
    private String sicilNo; // 25 karakter olacak

    @Getter
    @Setter
    private String naceCode; // 8 karakter olacak

    @Getter
    @Setter
    private String tehlikeSinifi;

    @Getter
    @Setter
    private int calisanSayisi;

    @Getter
    @Setter
    private int altIsverenToplamCalisanSayisi;

    @Getter
    @Setter
    private int altIsverenFirmaSayisi;

    @Getter
    @Setter
    private Contact isveren;
}
