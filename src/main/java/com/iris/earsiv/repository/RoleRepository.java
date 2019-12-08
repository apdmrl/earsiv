package com.iris.earsiv.repository;

import com.iris.earsiv.model.auth.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,String> {

}
