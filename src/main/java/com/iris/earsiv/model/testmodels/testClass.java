package com.iris.earsiv.model.testmodels;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Document(collection = "testModel")
@TypeAlias("testModel")
public class testClass {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @DBRef
    private firm1 firm1;

    @Getter
    @Setter
    @DBRef
    private firm2 firm2;
}
