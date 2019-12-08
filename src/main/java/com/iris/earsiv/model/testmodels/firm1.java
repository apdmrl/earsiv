package com.iris.earsiv.model.testmodels;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Document(collection = "firm1")
@TypeAlias("firm1")
public class firm1 {

    @Getter
    @Setter
    private String id;
}
