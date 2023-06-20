package com.ersms.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {
    private String url;
    private String name;
    private String description;
    private String userEmail;
}