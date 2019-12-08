package com.iris.earsiv.model.system;

import com.iris.earsiv.model.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Accessors(chain = true)
@Document("SystemInfo")
public class SystemInfo extends Base {

    @Indexed
    @Getter
    @Setter
    private InfoKey key;

    @Getter
    @Setter
    private String value;

    public static SystemInfo create() {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setId(UUID.randomUUID().toString());
        return systemInfo;
    }
}