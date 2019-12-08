package com.iris.earsiv.repository;

import com.iris.earsiv.model.firma.Firma;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FirmaRepository extends MongoRepository<Firma,String> {

    List<Firma> findBySubeId(String id);
}
