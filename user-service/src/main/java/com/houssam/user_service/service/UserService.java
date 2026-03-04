package com.houssam.user_service.service;

import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.WatchlistRequestDto;
import com.houssam.user_service.dto.WatchlistResponseDto;
import com.houssam.user_service.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserRequestDto dto);
    User getUserById(Long userId);
    List<User> getAllUser();
    User updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long userId);
    WatchlistResponseDto addToWatchlist(Long userId,WatchlistRequestDto dto);
    void removeFromWatchList(Long userId, String videoId);
    List<WatchlistResponseDto> getUserWatchlist(Long userId);
}
