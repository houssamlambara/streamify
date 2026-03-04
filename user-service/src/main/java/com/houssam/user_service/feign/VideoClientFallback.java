package com.houssam.user_service.feign;

import com.houssam.user_service.dto.VideoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class VideoClientFallback implements VideoClient{
    @Override
    public VideoDto getVideoById(String id) {
        log.warn("Fallback : video-service unavailable for id : {}",id);
        VideoDto fallback =new VideoDto();
        fallback.setId(id);
        fallback.setTitle("Video Unavailable");
        return fallback;
    }

    @Override
    public List<VideoDto> getVideos() {
        log.warn("Fallback : video-service unavailable");
        return Collections.emptyList();
    }
}
