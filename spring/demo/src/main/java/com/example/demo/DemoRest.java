package com.example.demo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DemoRest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "book/{isbn}")
    public BookRepository.Book home(@PathVariable("isbn") String isbn) {
        return bookRepository.getByIsbn(isbn);
    }

    @GetMapping(value = "getText", produces = MediaType.TEXT_HTML_VALUE)
    public String getText() {
        return "HELLO";
    }

    @GetMapping(value = "test", produces = MediaType.APPLICATION_JSON_VALUE)
    public StreamingResponseBody test() {
        AtomicLong count = new AtomicLong();
        return outputStream -> {
            while (count.get() < 10) {
                outputStream.write(objectMapper.writeValueAsBytes(bookRepository.getByIsbn(String.valueOf(count.getAndIncrement()))));
                outputStream.write('\n');
                outputStream.flush();
            }
        };
    }

    @GetMapping(value = "test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> test2() {
        AtomicLong count = new AtomicLong();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(outputStream -> {
            while (count.get() < 10) {
                outputStream.write(objectMapper.writeValueAsBytes(bookRepository.getByIsbn(String.valueOf(count.getAndIncrement()))));
                outputStream.write('\n');
                outputStream.flush();
            }
        });
    }

    @GetMapping(value = "/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] photo() throws IOException {
        File imageFile = Paths.get("D:\\privacy\\sideprojects\\spring\\demo\\src\\main\\resources\\image.png")
                .toFile();
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bao);
        return bao.toByteArray();
    }

    @PostMapping(value = "/photo")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getName();
        long size = file.getSize();
        System.out.println(fileName);
        System.out.println(size);
        return "Success";
    }

    //@GetMapping("foo")
    public Map<String, String> foo() {
        Map<String, String> m = new HashMap<>();
        m.put("keyfdf", null);
        return m;
    }
}
