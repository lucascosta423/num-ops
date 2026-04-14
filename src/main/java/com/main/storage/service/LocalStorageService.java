package com.main.storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.storage.local-path:/tmp/storage}")
    private String path;

    @Override
    public String upload(InputStream file, String fileName) {
        try {
            Path target = Paths.get(path, fileName);
            Files.copy(file, target);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            return Files.newInputStream(Paths.get(path, fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.delete(Paths.get(path, fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
