package com.houssam.user_service.repository;

import com.houssam.user_service.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
        boolean existsByVideoId(String videoId);

        boolean existsByUserIdAndVideoId(Long userId, String videoId);

        @Modifying
        @Query("DELETE FROM Watchlist w WHERE w.userId = :userId AND w.videoId = :videoId")
        void deleteByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") String videoId);
}
