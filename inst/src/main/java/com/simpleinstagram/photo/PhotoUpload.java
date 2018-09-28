package com.simpleinstagram.photo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.simpleinstagram.web.ControllerAdapter;

public class PhotoUpload extends ControllerAdapter {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File imageFile = Paths.get("D:\\privacy\\sideprojects\\instagram\\src\\main\\resources\\image.png")
                .toFile();
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bao);
        response.getOutputStream().write(bao.toByteArray());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            getAndStoreUploadFile(request);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void getAndStoreUploadFile(HttpServletRequest request) throws IOException, ServletException {
        // Get the part "file" from given request
        // Read the header content-disposition to retrieve the fileName
        // Store the uploaded file into directory
        Part part = request.getPart("file");
        String fileName = getFileName(part);

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("The filename is not specified");
        }

        try (OutputStream out = new FileOutputStream(
                Files.createFile(Paths.get("D:\\privacy\\sideprojects\\instagram\\src\\main\\resources", fileName)).toFile());
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
