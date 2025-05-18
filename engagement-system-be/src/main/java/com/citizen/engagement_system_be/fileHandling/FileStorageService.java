package com.citizen.engagement_system_be.fileHandling;

import com.citizen.engagement_system_be.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileStorageService {
    @Value("${uploads.directory}")
    private String root;

    public String save(MultipartFile file, String directory) {
        try {
            Path path = Paths.get(directory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage());
        }
        return directory;
    }
}
