package com.iris.earsiv.repository;

import com.iris.earsiv.model.system.InfoKey;
import com.iris.earsiv.model.system.SystemInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemInfoRepository extends MongoRepository<SystemInfo, String> {
    SystemInfo findByKey(InfoKey key);
}

