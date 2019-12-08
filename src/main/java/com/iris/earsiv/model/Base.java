package com.iris.earsiv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Base {

    @Id
    @Getter
    @Setter
    private String id;

    @LastModifiedDate
    @Getter
    @Setter
    private Date updated;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Base) {
            Base otherObj = (Base) obj;
            if (getId() == null || otherObj.getId() == null) {
                return false;
            } else {
                return getId().equals(otherObj.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

