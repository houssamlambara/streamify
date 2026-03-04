package com.houssam.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WatchlistRequestDto {
    @NotBlank(message = "video id is required")
    private long videoId;
}
