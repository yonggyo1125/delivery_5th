package com.codefactory.delivery.test3;

import com.codefactory.delivery.file.application.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
public class Ex01 {

    @Autowired
    FileUploadService service;

    MultipartFile[] files;

    @BeforeEach
    void init() {
        MultipartFile file1 = new MockMultipartFile("file", "test1.png", "image/png", new byte[] {1,2,3,4});
        MultipartFile file2 = new MockMultipartFile("file", "test2.png", "image/png", new byte[] {1,2,3,4});
        files = new MultipartFile[] { file1, file2 };
    }

    @Test
    void fileUploadTest() {
        service.process(files);
    }
}
