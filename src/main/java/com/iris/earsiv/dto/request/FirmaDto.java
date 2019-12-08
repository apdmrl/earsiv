package com.iris.earsiv.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iris.earsiv.model.Address;
import com.iris.earsiv.model.Status;
import com.iris.earsiv.model.firma.FirmaProfile;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmaDto {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private FirmaProfile profile;

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    private String subeId;

}
