package com.houssam.video_service.dto.response;

import com.houssam.video_service.enums.VideoCategory;
import com.houssam.video_service.enums.VideoType;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoResponseDTO {

    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private VideoType type;
    private VideoCategory category;
    private Double rating;
    private String director;
    private String cast;
    private String tmdbId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
