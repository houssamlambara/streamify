package com.houssam.user_service.controller;

import com.houssam.user_service.dto.*;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ===================== CRUD User =====================

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
                                           @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }

    // ===================== Watchlist (collègue) =====================

    @PostMapping("/{userId}/watchlist")
    public ResponseEntity<WatchlistResponseDto> addToWatchlist(@PathVariable Long userId,
                                                               @RequestBody @Valid WatchlistRequestDto dto) {
        return new ResponseEntity<>(userService.addToWatchlist(userId, dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Long userId,
                                                      @PathVariable String videoId) {
        userService.removeFromWatchList(userId, videoId);
        return ResponseEntity.ok("video remove from watchlist successfully");
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<List<WatchlistResponseDto>> getUserWatchlist(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserWatchlist(userId));
    }

    // ===================== WatchHistory (tes ajouts) =====================

    @PostMapping("/{id}/history")
    public ResponseEntity<WatchHistoryResponseDto> recordWatchHistory(
            @PathVariable("id") Long userId,
            @RequestBody WatchHistoryRequestDto dto) {
        return new ResponseEntity<>(userService.recordWatchHistory(userId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<WatchHistoryResponseDto>> getUserWatchHistory(
            @PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserWatchHistory(userId));
    }

    @GetMapping("/{id}/history/stats")
    public ResponseEntity<WatchStatsDto> getUserWatchStats(
            @PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserWatchStats(userId));
    }
}
