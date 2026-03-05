package com.houssam.user_service.service;

import com.houssam.user_service.dto.*;
import com.houssam.user_service.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserRequestDto dto);
    User getUserById(Long userId);
    List<User> getAllUser();
    User updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long userId);

    // Watchlist
    WatchlistResponseDto addToWatchlist(Long userId, WatchlistRequestDto dto);
    void removeFromWatchList(Long userId, String videoId);
    List<WatchlistResponseDto> getUserWatchlist(Long userId);

    // WatchHistory
    WatchHistoryResponseDto recordWatchHistory(Long userId, WatchHistoryRequestDto dto);
    List<WatchHistoryResponseDto> getUserWatchHistory(Long userId);
    WatchStatsDto getUserWatchStats(Long userId);
}
