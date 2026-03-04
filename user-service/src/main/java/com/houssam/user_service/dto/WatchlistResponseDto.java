package com.houssam.user_service.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class WatchlistResponseDto {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
}
