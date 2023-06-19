package com.ersms.app.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("user")
    private Object user;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("tags")
    private Object tags;
}
