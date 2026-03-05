package com.houssam.video_service.model;

import com.houssam.video_service.enums.VideoCategory;
import com.houssam.video_service.enums.VideoType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoCategory category;

    private Double rating;
    private String director;

    @Column(name = "cast_list", columnDefinition = "TEXT")
    private String cast;
    private String tmdbId;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
