package com.iris.earsiv.repository;

import com.iris.earsiv.model.FileResource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileResourceRepository extends MongoRepository<FileResource,String> {
}
