package com.houssam.video_service.mapper;

import com.houssam.video_service.dto.request.VideoRequestDTO;
import com.houssam.video_service.dto.response.VideoResponseDTO;
import com.houssam.video_service.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public VideoResponseDTO toDto(Video video) {
        if (video == null) {
            return null;
        }

        return VideoResponseDTO.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .thumbnailUrl(video.getThumbnailUrl())
                .trailerUrl(video.getTrailerUrl())
                .duration(video.getDuration())
                .releaseYear(video.getReleaseYear())
                .type(video.getType())
                .category(video.getCategory())
                .rating(video.getRating())
                .director(video.getDirector())
                .cast(video.getCast())
                .tmdbId(video.getTmdbId())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .build();
    }

    public Video toEntity(VideoRequestDTO requestDto) {
        if (requestDto == null) {
            return null;
        }

        return Video.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .thumbnailUrl(requestDto.getThumbnailUrl())
                .trailerUrl(requestDto.getTrailerUrl())
                .duration(requestDto.getDuration())
                .releaseYear(requestDto.getReleaseYear())
                .type(requestDto.getType())
                .category(requestDto.getCategory())
                .rating(requestDto.getRating())
                .director(requestDto.getDirector())
                .cast(requestDto.getCast())
                .tmdbId(requestDto.getTmdbId())
                .build();
    }
}
