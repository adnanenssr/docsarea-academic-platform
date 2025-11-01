package com.docsarea.service;

import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;

@Service
public class FileUpload {

    @Autowired
    ThumbnailGenerator thumbnailGenerator ;


    public String getFileName (MultipartFile file ){
        String fileName =  file.getOriginalFilename() ;
        if (fileName.isBlank()) throw new RuntimeException("fine name is missing") ;
        else return fileName ;
    }

    public String getExtension (String name){

        int ind = name.lastIndexOf(".") ;
        if(ind == -1 ) throw new RuntimeException( "invalid file name!" ) ;
        return name.substring(ind ) ;
    }

    public Long getFileSize (MultipartFile file ){
        if (file.getSize() == 0) throw new RuntimeException("File is Corrupted ") ;
        return file.getSize() ;
    }

    public String getDirectory (String user) throws IOException {
        System.out.println(user);

        Path directory = Paths.get(System.getProperty("user.dir") , "Upload" , user ) ;
        try {
            Files.createDirectories(directory);
        }
        catch (Exception e){
            throw new RuntimeException("couldn't create directory ") ;
        }
        return directory.toString() ;

    }

    public Path getFilePath ( String directory , String fileId , String extension ){
        return Paths.get(directory , fileId.concat(extension));
    }

    public StorageFileDto saveFile (MultipartFile file , String fileId  ) throws IOException {
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        String fileName = getFileName(file) ;
        String extension = getExtension(fileName) ;
        String directory = getDirectory(user)  ;
        Path filePath = getFilePath(directory , fileId , extension ) ;
        Path thumbnail = getFilePath(directory , fileId , ".jpg") ;
        Long storage = getFileSize(file) ;


        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e)
        {
            System.out.println("file could not be saved");
            throw e ;
        };

        thumbnailGenerator.fileThumbnail(filePath.toString() , thumbnail.toString() );




        return  new StorageFileDto(filePath.toString() , extension , fileName , storage , thumbnail.toString()) ;

    }
}
