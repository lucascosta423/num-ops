package com.main.storage.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.storage.local-path:./storage/faturas}")
    private String path;

    private Path root;

    @PostConstruct
    public void init() {
        try {
            root = Paths.get(path);
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar diretório de storage", e);
        }
    }

    @Override
    public String upload(InputStream file, String fileName) {
        try {
            if (fileName == null || fileName.isBlank()) {
                throw new RuntimeException("Nome do arquivo inválido");
            }

            String safeFileName = Path.of(fileName).getFileName().toString();

            Path target = root.resolve(safeFileName);

            Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);

            return safeFileName;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            Path file = root.resolve(fileName);

            if (!Files.exists(file)) {
                throw new RuntimeException("Arquivo não encontrado");
            }

            return Files.newInputStream(file);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar arquivo", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.deleteIfExists(root.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo", e);
        }
    }
}
