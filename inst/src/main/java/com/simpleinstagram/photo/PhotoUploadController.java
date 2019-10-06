package com.simpleinstagram.photo;

import com.simpleinstagram.web.ControllerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PhotoUploadController extends ControllerAdapter {

    private PhotoService photoService;

    public PhotoUploadController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.getOutputStream().write(photoService.loadPhotos());
        List<Long> ids = photoService.loadAllPhotoIds();
        response.getOutputStream().write(ids.toString().getBytes());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            photoService.uploadFile(request);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
