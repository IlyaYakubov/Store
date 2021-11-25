package com.example.demo.repositories;

import com.example.demo.models.Image;

import java.util.LinkedHashMap;
import java.util.Map;

public class ImageDAO {

    public static ImageDAO INSTANCE = new ImageDAO();

    private Map<String, Image> images = new LinkedHashMap<>();

    private ImageDAO() {
        // do nothing
    }

    public void add(Image image) {
        images.put(image.getName(), image);
    }
}
