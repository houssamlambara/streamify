package com.houssam.video_service.dto;

import com.houssam.video_service.enums.VideoCategory;
import com.houssam.video_service.enums.VideoType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequestDTO {

    @NotBlank(message = "Le titres est obligatoire")
    private String title;

    private String description;
    private String thumbnailUrl;
    private String trailerUrl;

    @Positive(message = "La durée doit être un nombre positif")
    private Integer duration;

    private Integer releaseYear;

    @NotNull (message = "Le type de vidéo est obligatoire")
    private VideoType type;

    @NotNull(message = "La catégorie de vidéo est obligatoire")
    private VideoCategory category;


    @DecimalMin(value = "0.0", inclusive = true, message = "La note doit être au moins 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Lanote doit être au plus 10.0")
    private Double rating;

    private String director;
    private String cast;
    private String tmdbId;
}

