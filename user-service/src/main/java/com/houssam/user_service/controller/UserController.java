package com.houssam.user_service.controller;

import com.houssam.user_service.dto.UserRequestDto;
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
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    // 2. Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    // 3. Get All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // 4. Update User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    // 5. Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @PostMapping("/{userId}/watchlist")
    public ResponseEntity<WatchlistResponseDto> addToWatchlist(@PathVariable Long userId,@RequestBody @Valid WatchlistRequestDto dto){
        return new ResponseEntity<>(userService.addToWatchlist(userId,dto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Long userId, @PathVariable String videoId){
        userService.removeFromWatchList(userId,videoId);
        return ResponseEntity.ok("video remove from watchlist successfully");
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<List<WatchlistResponseDto>> getUserWatchlist(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserWatchlist(userId));
    }


}
