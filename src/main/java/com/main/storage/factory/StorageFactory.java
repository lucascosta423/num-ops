package com.main.storage.factory;

import com.main.storage.service.LocalStorageService;
import com.main.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class StorageFactory {

//    @Value("${app.storage.type}")
    private String type;

    private final LocalStorageService local;

    public StorageFactory(LocalStorageService local) {
        this.local = local;

    }

    public StorageService getStorage() {
        return local;
    }
}
