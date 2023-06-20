package com.ersms.app.data;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Photo {
    private Long id;
    private String name;
    private String userEmail;
    private String description;
    private MultipartFile file;
    private String url;
    private Metadata metadata;
    private List<Tag> tags;
}
