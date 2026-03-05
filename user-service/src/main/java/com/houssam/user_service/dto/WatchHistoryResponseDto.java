package com.houssam.user_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WatchHistoryResponseDto {
    private Long id;
    private Long userId;
    private String videoId;
    private LocalDateTime watchedAt;
    private Integer progressTime;
    private Boolean completed;
    private VideoDto video;
}

