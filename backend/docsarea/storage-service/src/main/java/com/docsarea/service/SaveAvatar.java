package com.docsarea.service;

import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SaveAvatar {


    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    public String saveAvatar (MultipartFile file) throws IOException {
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Path directory = Path.of("Upload", user,"avatar");
        Path path = Path.of(directory.toString() , user + ".jpg");
        try {
            Files.createDirectories(directory);
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) throw new IOException("image is not valid");
            BufferedImage result = resizeImage(image, 600, 600);
            File output = new File(path.toAbsolutePath().toString());
            ImageIO.write(result, "jpg", output);
        }catch(IOException e){
            throw e ;
        }

        return path.toString() ;
    }

    public String saveGroupAvatar (MultipartFile file , String groupId) throws IOException {
        Path directory = Path.of("Upload", groupId,"avatar");
        Path path = Path.of(directory.toString() , groupId + ".jpg");
        try {
            Files.createDirectories(directory);
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) throw new IOException("image is not valid");
            BufferedImage result = resizeImage(image, 600, 600);
            File output = new File(path.toAbsolutePath().toString());
            ImageIO.write(result, "jpg", output);
        }catch(IOException e){
            throw e ;
        }

        return path.toString() ;
    }


}
