package com.ecommerce.project.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws Exception {
        // Get original file name
        String originalFileName = file.getOriginalFilename();
        //Generate a random file name
        String randomId = UUID.randomUUID().toString();
        // mat.jps --> 1234 --> 1234.jpg
        String newFileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + newFileName;
        // Check if path already exist otherwise create
        File folder = new File(path);
        if(!folder.exists()) folder.mkdir();

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFileName;
    }

}
