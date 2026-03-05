package com.houssam.user_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WatchHistoryRequestDto {

    @NotBlank(message = "video id is required")
    private String videoId;

    @NotNull(message = "progress time is required")
    @Min(value = 0, message = "progress time must be positive")
    private Integer progressTime;

    @NotNull(message = "completed status is required")
    private Boolean completed;
}

