package com.bikkadit.services.impl;

import com.bikkadit.exception.BadApiRequest;
import com.bikkadit.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

  private   Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        //abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename :{}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExension=filename+extension;
        String fullPathWithFileName=path+File.separator+fileNameWithExension;

        if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")){
            //file save
            File folder=new File(path);
            if(!folder.exists()){
                //create the folder
                folder.mkdirs();
            }
            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExension;
        }else{
            throw new BadApiRequest("File with this "+extension+"not allowed !!");
        }



    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
    String fullpath=path+File.separator +name;
    InputStream inputStream=new FileInputStream(fullpath);
        return inputStream;
    }
}
