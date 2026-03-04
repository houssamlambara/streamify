package com.houssam.user_service.feign;

import com.houssam.user_service.dto.VideoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "video-service")
public interface VideoClient {

    @GetMapping("/api/videos/{id}")
    VideoDto getVideoById(@PathVariable String id);

    @GetMapping("/api/videos")
    List<VideoDto> getVideos();
}
