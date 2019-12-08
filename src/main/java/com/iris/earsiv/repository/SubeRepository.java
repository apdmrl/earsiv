package com.iris.earsiv.repository;

import com.iris.earsiv.model.Sube;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubeRepository extends MongoRepository<Sube,String> {

    Sube findSubeById(String id);
}
