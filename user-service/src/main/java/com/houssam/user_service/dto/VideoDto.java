package com.houssam.user_service.dto;

import lombok.Data;

@Data
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private String type;
    private String category;
    private Double rating;
    private String director;
}
