package com.houssam.user_service.controller;

import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.UserResponseDto;
import com.houssam.user_service.dto.WatchlistRequestDto;
import com.houssam.user_service.dto.WatchlistResponseDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto user = userService.createUser(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // 2. Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    // 3. Get All Users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // 4. Update User
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") long id,
            @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    // 5. Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PostMapping("/{userId}/watchlist")
    public ResponseEntity<WatchlistResponseDto> addToWatchlist(@RequestBody @Valid WatchlistRequestDto dto,
            @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.addToWatchlist(userId, dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable("userId") Long userId,
            @PathVariable("videoId") String videoId) {
        userService.removeFromWatchList(userId, videoId);
        return ResponseEntity.ok("video remove from watchlist successfully");
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<List<WatchlistResponseDto>> getUserWatchlist(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserWatchlist(userId));
    }

}
