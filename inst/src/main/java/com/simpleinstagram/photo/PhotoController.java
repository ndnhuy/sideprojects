package com.simpleinstagram.photo;

import com.simpleinstagram.web.ControllerAdapter;
import com.simpleinstagram.web.handler.Controller;
import com.simpleinstagram.web.handler.RequestMapping;
import com.simpleinstagram.web.handler.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PhotoController extends ControllerAdapter {

    private PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @RequestMapping(path = "/photo", requestMethod = RequestMethod.GET)
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
