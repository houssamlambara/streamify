package com.houssam.user_service.service;

import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.UserResponseDto;
import com.houssam.user_service.dto.WatchlistRequestDto;
import com.houssam.user_service.dto.WatchlistResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUserById(Long userId);
    List<UserResponseDto> getAllUser();
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long userId);
    WatchlistResponseDto addToWatchlist(Long userId,WatchlistRequestDto dto);
    void removeFromWatchList(Long userId, String videoId);
    List<WatchlistResponseDto> getUserWatchlist(Long userId);
}
