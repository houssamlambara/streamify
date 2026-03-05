package com.houssam.user_service.repository;

import com.houssam.user_service.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    List<WatchHistory> findByUserId(Long userId);

    Optional<WatchHistory> findByUserIdAndVideoId(Long userId, String videoId);

    boolean existsByUserIdAndVideoId(Long userId, String videoId);

    long countByUserId(Long userId);

    long countByUserIdAndCompleted(Long userId, Boolean completed);

    @Query("SELECT COALESCE(SUM(w.progressTime), 0) FROM WatchHistory w WHERE w.userId = :userId")
    Long sumProgressTimeByUserId(@Param("userId") Long userId);
}

