package com.ersms.app.data;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Photo {
    private int id;
    private String name;
    private String description;
    private MultipartFile file;
    private String url;
    private Object metadata;
    private List<String> tags;
}
