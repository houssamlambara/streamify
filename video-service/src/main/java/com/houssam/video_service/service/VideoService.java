package com.houssam.video_service.service;

import com.houssam.video_service.dto.request.VideoRequestDTO;
import com.houssam.video_service.dto.response.VideoResponseDTO;

import java.util.List;

public interface VideoService {

    VideoResponseDTO createVideo(VideoRequestDTO request);

    VideoResponseDTO getVideoById(String id);

    List<VideoResponseDTO> getAllVideos();

    VideoResponseDTO updateVideo(String id, VideoRequestDTO request);

    void deleteVideo(String id);

    List<VideoResponseDTO> searchByTitle(String title);

    List<VideoResponseDTO> getByType(String type);

    List<VideoResponseDTO> getByCategory(String category);

}
