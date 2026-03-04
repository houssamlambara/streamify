package com.houssam.user_service.service;

import com.houssam.user_service.exception.EmailAlreadyExistsException;
import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.WatchlistRequestDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.entity.Watchlist;
import com.houssam.user_service.exception.VideoAlreadyInWatchlist;
import com.houssam.user_service.repository.UserRepository;
import com.houssam.user_service.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;

    @Override
    @Transactional
    public User createUser(UserRequestDto dto) {

        boolean exists = userRepository.existsByEmail(dto.getEmail());

        if(exists){
            throw new EmailAlreadyExistsException("email already exists : "+dto.getEmail());
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
                .orElseThrow(() -> new RuntimeException("user not found, id : "+userId));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found, id :"+id)
        );

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

    @Override
    @Transactional
    public Watchlist addToWatchlist(Long userId,WatchlistRequestDto dto) {
        userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("user not found id : "+userId)
        );

        boolean exists = watchlistRepository.existsByVideoId(dto.getVideoId());

        if(exists){
            throw new VideoAlreadyInWatchlist("video already in watchlist : "+dto.getVideoId());
        }

        Watchlist watchlist = Watchlist.builder()
                .userId(userId)
                .videoId(dto.getVideoId())
                .build();

        return watchlistRepository.save(watchlist);

    }

    @Override
    public void removeFromWatchList(Long id) {

    }
}
