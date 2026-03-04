package com.houssam.user_service.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class WatchlistDto {
    @NotBlank(message = "user id is required")
    private long userId;

    @NotBlank(message = "video id is required")
    private long videoId;

    private LocalDateTime addedAt;


}
