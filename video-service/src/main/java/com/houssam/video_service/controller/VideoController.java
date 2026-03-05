package com.houssam.video_service.controller;

import com.houssam.video_service.dto.request.VideoRequestDTO;
import com.houssam.video_service.dto.response.VideoResponseDTO;
import com.houssam.video_service.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoResponseDTO>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable String id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @PostMapping
    public ResponseEntity<VideoResponseDTO> createVideo(@Valid @RequestBody VideoRequestDTO videoRequestDTO) {
        VideoResponseDTO createdVideo = videoService.createVideo(videoRequestDTO);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> updateVideo(
            @PathVariable String id,
            @Valid @RequestBody VideoRequestDTO videoRequestDTO) {
        return ResponseEntity.ok(videoService.updateVideo(id, videoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable String id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<VideoResponseDTO>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(videoService.searchByTitle(title));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<VideoResponseDTO>> filterVideos(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        if (type != null) {
            return ResponseEntity.ok(videoService.getByType(type));
        } else if (category != null) {
            return ResponseEntity.ok(videoService.getByCategory(category));
        }
        return ResponseEntity.ok(videoService.getAllVideos());
    }
}
