package com.akshayram.cloudstorage.services;

import com.akshayram.cloudstorage.mapper.FileMapper;
import com.akshayram.cloudstorage.mapper.UserMapper;
import com.akshayram.cloudstorage.model.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    /*fetch the file name*/
    public String[] getFileListings(Integer userId) {
        return fileMapper.getFileListings(userId);
    }

    /*add a multipartFile*/
    public void addFile(MultipartFile multipartFile, String userName) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userMapper.getUser(userName).getUserId();
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.insert(file);
    }

    /*fetch a file*/
    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    /*remove a file*/
    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
