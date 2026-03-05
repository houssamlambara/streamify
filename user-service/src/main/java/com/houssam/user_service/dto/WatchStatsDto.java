package com.houssam.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WatchStatsDto {
    private Long userId;
    private long totalVideosWatched;
    private long completedVideos;
    private long incompleteVideos;
    private long totalProgressTime;
    private double completionRate;
}

