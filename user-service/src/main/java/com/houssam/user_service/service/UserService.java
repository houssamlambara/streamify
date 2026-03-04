package com.houssam.user_service.service;

import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.WatchlistRequestDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.entity.Watchlist;

import java.util.List;

public interface UserService {
    User createUser(UserRequestDto dto);
    User getUserById(Long userId);
    List<User> getAllUser();
    User updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long userId);
    Watchlist addToWatchlist(Long userId,WatchlistRequestDto dto);
    void removeFromWatchList(Long id);
}
