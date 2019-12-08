package com.iris.earsiv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iris.earsiv.model.auth.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Document(collection = "FileResource")
@TypeAlias("FileResource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResource extends Base{

    // Reserved for file name
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private ExtensionType extension;

    // URL to access file on disk
    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private FileResourceType resourceType;

    // Base64 file content
    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    @NotNull
    private FilePrivacy filePrivacy = FilePrivacy.PRIVATE;

    @Getter
    @Setter
    @DBRef
    private Account creator;
}