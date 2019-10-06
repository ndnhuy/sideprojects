package com.simpleinstagram.photo;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PhotoService {

    private PhotoDAO photoDAO;

    public PhotoService(PhotoDAO photoDAO) {
        this.photoDAO = photoDAO;
    }

    public byte[] loadPhotos() throws IOException {
        return photoDAO.loadPhotos();
    }

    public List<Long> loadAllPhotoIds() {
        return photoDAO.loadAllPhotoIds();
    }

    public void uploadFile(HttpServletRequest request) throws IOException, ServletException {
        // Get the part "file" from given request
        // Read the header content-disposition to retrieve the fileName
        // Store the uploaded file into directory
        Part part = request.getPart("file");
        if (part == null) {
            throw new IllegalArgumentException("File is required");
        }
        String fileName = getFileName(part);

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("The filename is not specified");
        }
        try (OutputStream out = new FileOutputStream(
                Files.createFile(Paths.get("/Users/administrator/huynguyen/sideprojects/inst/src/main/resources", fileName)).toFile());
             InputStream fileContent = part.getInputStream()) {
            int read;
            byte[] byteArr = new byte[1024];
            while ((read = fileContent.read(byteArr)) != -1) {
                out.write(byteArr, 0, read);
            }
            out.flush();
        }
    }

    private String getFileName(Part part) {
        String headerContents = part.getHeader("content-disposition");
        for (String headerContent : headerContents.split(";")) {
            if (headerContent.trim().startsWith("filename")) {
                return headerContent.substring(headerContent.indexOf('=') + 1)
                        .replaceAll("\"", "")
                        .trim();
            }
        }
        return "";
    }
}
