package com.iris.earsiv.model;

import com.iris.earsiv.model.auth.Account;
import com.iris.earsiv.model.firma.Firma;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Accessors(chain = true)
@Document(collection = "Sube")
@TypeAlias("Sube")
public class Sube extends Base{
    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    private Address address;

    @Getter
    @Setter
    @DBRef
    private List<FileResource> files;

    @Getter
    @Setter
    @DBRef
    private List<Firma> firmalar;

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    @DBRef
    private Account subeAdmini;

    public boolean isEnabled() {
        return status == Status.ENABLED;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.status = Status.ENABLED;
        } else {
            this.status = Status.DISABLED;
        }
    }

}
