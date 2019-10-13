package com.simpleinstagram.photo;

import com.simpleinstagram.jdbc.JdbcTemplate;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhotoDAO {

    private DataSource dataSource;

    public PhotoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] loadPhotos() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("sample_image.jpeg");
        BufferedImage bufferedImage = ImageIO.read(is);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bao);
        return bao.toByteArray();
    }

    public List<Long> loadAllPhotoIds() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(conn -> conn.prepareStatement("select id from photo;"), ps -> {
            List<Long> ids = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
            return ids;
        });
    }
}
