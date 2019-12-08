package com.iris.earsiv.model.firma;

import com.iris.earsiv.model.Base;
import com.iris.earsiv.model.file.FileResource;
import com.iris.earsiv.model.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Accessors(chain = true)
@Document(collection = "Firma")
@TypeAlias("Firma")
public class Firma extends Base {

    @Getter
    @Setter
    private FirmaProfile profile;

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    @DBRef
    private List<FileResource> files;

    @Getter
    @Setter
    @Indexed
    private String subeId;

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
