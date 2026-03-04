package com.houssam.user_service.service;

import com.houssam.user_service.dto.UserDto;
import com.houssam.user_service.dto.WatchlistDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.entity.Watchlist;

public interface UserService {
    User createUser(UserDto dto);
    User getUserById(long userId);
    User getAllUser();
    User updateUser(UserDto dto);
    void deleteUser(long userId);

    Watchlist addToWatchlist(WatchlistDto dto);
    void removeFromWatchlist(long watchlistId);
}
