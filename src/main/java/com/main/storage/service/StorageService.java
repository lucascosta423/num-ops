package com.main.storage.service;

import java.io.InputStream;

public interface StorageService {

    String upload(InputStream file, String fileName);

    InputStream download(String fileName);

    void delete(String fileName);
}
