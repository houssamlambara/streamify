package com.houssam.user_service.service;

import com.houssam.user_service.dto.*;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.entity.WatchHistory;
import com.houssam.user_service.entity.Watchlist;
import com.houssam.user_service.exception.EmailAlreadyExistsException;
import com.houssam.user_service.exception.VideoAlreadyInWatchlist;
import com.houssam.user_service.feign.VideoClient;
import com.houssam.user_service.repository.UserRepository;
import com.houssam.user_service.repository.WatchHistoryRepository;
import com.houssam.user_service.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;
    private final WatchHistoryRepository watchHistoryRepository;
    private final VideoClient videoClient;

    // ===================== CRUD User =====================

    @Override
    @Transactional
    public User createUser(UserRequestDto dto) {
        boolean exists = userRepository.existsByEmail(dto.getEmail());
        if (exists) {
            throw new EmailAlreadyExistsException("email already exists : " + dto.getEmail());
        }
        User user = User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found, id : " + userId));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found, id : " + id));
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // ===================== Watchlist =====================

    @Override
    @Transactional
    public WatchlistResponseDto addToWatchlist(Long userId, WatchlistRequestDto dto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        boolean exists = watchlistRepository.existsByVideoId(dto.getVideoId());
        if (exists) {
            throw new VideoAlreadyInWatchlist("video already in watchlist : " + dto.getVideoId());
        }

        Watchlist watchlist = Watchlist.builder()
                .userId(userId)
                .videoId(dto.getVideoId())
                .build();

        return enrichWatchlist(watchlistRepository.save(watchlist));
    }

    @Override
    @Transactional
    public void removeFromWatchList(Long userId, String videoId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        boolean existInWatch = watchlistRepository.existsByUserIdAndVideoId(userId, videoId);
        if (existInWatch) {
            watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
        }
    }

    @Override
    public List<WatchlistResponseDto> getUserWatchlist(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        return watchlistRepository.findAll()
                .stream()
                .filter(w -> w.getUserId() == userId)
                .map(this::enrichWatchlist)
                .collect(Collectors.toList());
    }

    private WatchlistResponseDto enrichWatchlist(Watchlist watchlist) {
        VideoDto videoDto = null;
        try {
            videoDto = videoClient.getVideoById(watchlist.getVideoId());
        } catch (Exception e) {
            log.warn("could not fetch video {} from video-service", watchlist.getVideoId());
        }
        return WatchlistResponseDto.builder()
                .id(watchlist.getId())
                .videoId(watchlist.getVideoId())
                .userId(watchlist.getUserId())
                .addedAt(watchlist.getAddedAt())
                .video(videoDto)
                .build();
    }

    // ===================== WatchHistory =====================

    @Override
    @Transactional
    public WatchHistoryResponseDto recordWatchHistory(Long userId, WatchHistoryRequestDto dto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        // Si l'entrée existe déjà pour cette vidéo, on met à jour la progression
        Optional<WatchHistory> existing = watchHistoryRepository.findByUserIdAndVideoId(userId, dto.getVideoId());

        WatchHistory watchHistory;
        if (existing.isPresent()) {
            watchHistory = existing.get();
            watchHistory.setProgressTime(dto.getProgressTime());
            watchHistory.setCompleted(dto.getCompleted());
        } else {
            watchHistory = WatchHistory.builder()
                    .userId(userId)
                    .videoId(dto.getVideoId())
                    .progressTime(dto.getProgressTime())
                    .completed(dto.getCompleted())
                    .build();
        }

        return enrichWatchHistory(watchHistoryRepository.save(watchHistory));
    }

    @Override
    public List<WatchHistoryResponseDto> getUserWatchHistory(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        return watchHistoryRepository.findByUserId(userId)
                .stream()
                .map(this::enrichWatchHistory)
                .collect(Collectors.toList());
    }

    @Override
    public WatchStatsDto getUserWatchStats(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found id : " + userId));

        long total       = watchHistoryRepository.countByUserId(userId);
        long completed   = watchHistoryRepository.countByUserIdAndCompleted(userId, true);
        long incomplete  = total - completed;
        long totalTime   = watchHistoryRepository.sumProgressTimeByUserId(userId);
        double rate      = total > 0 ? (double) completed / total * 100 : 0.0;

        return WatchStatsDto.builder()
                .userId(userId)
                .totalVideosWatched(total)
                .completedVideos(completed)
                .incompleteVideos(incomplete)
                .totalProgressTime(totalTime)
                .completionRate(Math.round(rate * 100.0) / 100.0)
                .build();
    }

    private WatchHistoryResponseDto enrichWatchHistory(WatchHistory watchHistory) {
        VideoDto videoDto = null;
        try {
            videoDto = videoClient.getVideoById(watchHistory.getVideoId());
        } catch (Exception e) {
            log.warn("could not fetch video {} from video-service", watchHistory.getVideoId());
        }
        return WatchHistoryResponseDto.builder()
                .id(watchHistory.getId())
                .userId(watchHistory.getUserId())
                .videoId(watchHistory.getVideoId())
                .watchedAt(watchHistory.getWatchedAt())
                .progressTime(watchHistory.getProgressTime())
                .completed(watchHistory.getCompleted())
                .video(videoDto)
                .build();
    }
}

