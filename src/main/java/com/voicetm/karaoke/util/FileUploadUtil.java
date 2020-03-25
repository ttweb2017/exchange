package com.voicetm.karaoke.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {
    public static String saveFile(MultipartFile uploadFile, String uploadPath, String filePath) throws IOException {

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File catalogDir = new File(uploadDir + "/" + filePath);

        if (!catalogDir.exists()) {
            catalogDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + uploadFile.getOriginalFilename();

        uploadFile.transferTo(new File(uploadPath + "/" + filePath + "/" + resultFilename));

        return resultFilename;
    }

    public static void deleteFile(String filePath) {
        File deleteDir = new File(filePath);

        if (deleteDir.exists()) {
            deleteDir.delete();
        }
    }
}
