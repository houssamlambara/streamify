package com.houssam.video_service.service.implementation;

import com.houssam.video_service.dto.request.VideoRequestDTO;
import com.houssam.video_service.dto.response.TmdbVideoResponseDTO;
import com.houssam.video_service.dto.response.VideoResponseDTO;
import com.houssam.video_service.enums.VideoCategory;
import com.houssam.video_service.enums.VideoType;
import com.houssam.video_service.exception.VideoNotFoundException;
import com.houssam.video_service.mapper.VideoMapper;
import com.houssam.video_service.model.Video;
import com.houssam.video_service.repository.VideoRepository;
import com.houssam.video_service.service.TmdbService;
import com.houssam.video_service.service.VideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final TmdbService tmdbService;

    @Override
    public VideoResponseDTO createVideo(VideoRequestDTO request) {
        Video video = videoMapper.toEntity(request);
        video.setCreatedAt(LocalDateTime.now());

        enrichWithTmdbData(video);

        Video savedVideo = videoRepository.save(video);
        return videoMapper.toDto(savedVideo);
    }

    @Override
    public VideoResponseDTO getVideoById(String id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found: " + id));
        return videoMapper.toDto(video);
    }

    @Override
    public List<VideoResponseDTO> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResponseDTO updateVideo(String id, VideoRequestDTO request) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found: " + id));

        existingVideo.setTitle(request.getTitle());
        existingVideo.setDescription(request.getDescription());
        existingVideo.setThumbnailUrl(request.getThumbnailUrl());
        existingVideo.setTrailerUrl(request.getTrailerUrl());
        existingVideo.setDuration(request.getDuration());
        existingVideo.setReleaseYear(request.getReleaseYear());
        existingVideo.setType(request.getType());
        existingVideo.setCategory(request.getCategory());
        existingVideo.setRating(request.getRating());
        existingVideo.setDirector(request.getDirector());
        existingVideo.setCast(request.getCast());

        if (request.getTmdbId() != null && !request.getTmdbId().equals(existingVideo.getTmdbId())) {
            existingVideo.setTmdbId(request.getTmdbId());
            enrichWithTmdbData(existingVideo);
        }

        existingVideo.setUpdatedAt(LocalDateTime.now());

        Video updatedVideo = videoRepository.save(existingVideo);
        return videoMapper.toDto(updatedVideo);
    }

    @Override
    public void deleteVideo(String id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException("Video not found: " + id);
        }
        videoRepository.deleteById(id);
    }

    @Override
    public List<VideoResponseDTO> searchByTitle(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoResponseDTO> getByType(String type) {
        return videoRepository.findByType(VideoType.valueOf(type.toUpperCase())).stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoResponseDTO> getByCategory(String category) {
        return videoRepository.findByCategory(VideoCategory.valueOf(category.toUpperCase())).stream()
                .map(videoMapper::toDto)
                .collect(Collectors.toList());
    }

    private void enrichWithTmdbData(Video video) {
        if (video.getTmdbId() != null && !video.getTmdbId().isEmpty()) {
            if (video.getType() == VideoType.FILM) {
                try {
                    TmdbVideoResponseDTO metadata = tmdbService.MovieById(video.getTmdbId());

                    if (metadata != null) {
                        if (video.getDescription() == null || video.getDescription().isEmpty()) {
                            video.setDescription(metadata.getDescription());
                        }
                        if (video.getRating() == null && metadata.getVoteAverage() != null) {
                            video.setRating(metadata.getVoteAverage());
                        }
                        if (video.getThumbnailUrl() == null && metadata.getPosterPath() != null) {
                            video.setThumbnailUrl(tmdbService.buildImageUrl(metadata.getPosterPath()));
                        }
                        if (video.getReleaseYear() == null && metadata.getReleaseDate() != null
                                && metadata.getReleaseDate().length() >= 4) {
                            video.setReleaseYear(Integer.parseInt(metadata.getReleaseDate().substring(0, 4)));
                        }
                    }
                } catch (Exception e) {
                    log.error("Failed to fetch TMDb metadata for film ID {}", video.getTmdbId(), e);
                }
            } else {
                log.info(
                        "TMDb enrichment for TV series is currently unsupported (TmdbService only has Movie queries).");
            }
        }
    }
}
