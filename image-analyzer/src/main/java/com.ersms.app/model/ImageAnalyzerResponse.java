package com.ersms.app.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageAnalyzerResponse {
    private Long id;
    private List<String> tags;
}
