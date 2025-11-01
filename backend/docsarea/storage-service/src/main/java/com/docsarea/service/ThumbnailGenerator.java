package com.docsarea.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ThumbnailGenerator {

    public void fileThumbnail(String filePath , String outputPath){
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage image = renderer.renderImageWithDPI(0, 300);

            Thumbnails.of(image)
                    .size(800, 1000)
                    .outputFormat("jpg")
                    .toFile(outputPath);

            System.out.println("✅ Saved resized image: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
