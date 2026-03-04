package com.houssam.user_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WatchlistResponseDto {
    private Long id;
    private Long userId;
    private String videoId;
    private LocalDateTime addedAt;
    private VideoDto video;
}
