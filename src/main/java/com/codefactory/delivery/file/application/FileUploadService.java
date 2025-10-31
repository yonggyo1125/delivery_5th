package com.codefactory.delivery.file.application;

import com.codefactory.delivery.file.infrastructure.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileProperties properties;

    public void process(MultipartFile[] files) {

        for(MultipartFile file : files) {
            try {
                String fileName = file.getOriginalFilename();
                File uploadFile = new File(properties.getPath() + "/" + fileName);
                file.transferTo(uploadFile);

            } catch (IOException e) {}
        }
    }
}
