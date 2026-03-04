package com.houssam.user_service.repository;

import com.houssam.user_service.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {
    boolean existsByVideoId(Long videoId);
}
