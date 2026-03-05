package com.houssam.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistResponseDto {
    private Long id;
    private Long userId;
    private String videoId;
    private LocalDateTime addedAt;
    private VideoDto video;
}
