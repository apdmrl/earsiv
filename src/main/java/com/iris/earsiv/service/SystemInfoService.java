package com.iris.earsiv.service;

import com.iris.earsiv.model.system.InfoKey;
import com.iris.earsiv.model.system.SystemInfo;
import com.iris.earsiv.repository.SystemInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class SystemInfoService {

    @Autowired
    private SystemInfoRepository systemInfoRepository;

    public List<SystemInfo> list() {
        return systemInfoRepository.findAll();
    }

    public SystemInfo get(InfoKey key) {
        return systemInfoRepository.findByKey(key);
    }

    public SystemInfo save(SystemInfo systemInfo) {
        SystemInfo sysInfo = systemInfoRepository.findByKey(systemInfo.getKey());
        if (sysInfo == null) {
            sysInfo = new SystemInfo()
                    .setKey(systemInfo.getKey())
                    .setValue(systemInfo.getValue());
            sysInfo.setId(UUID.randomUUID().toString());
        } else {
            sysInfo.setValue(systemInfo.getValue());
        }
        return systemInfoRepository.save(sysInfo);
    }

    public void remove(InfoKey key) {
        SystemInfo sysInfo = systemInfoRepository.findByKey(key);

        if (sysInfo != null) {
            systemInfoRepository.delete(sysInfo);
        }
    }

}
