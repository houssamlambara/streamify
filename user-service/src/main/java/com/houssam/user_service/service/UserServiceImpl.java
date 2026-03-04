package com.houssam.user_service.service;

import com.houssam.user_service.EmailAlreadyExistsException;
import com.houssam.user_service.dto.UserDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(UserDto dto) {

        boolean exists = repository.existsByEmail(dto.getEmail());

        if(exists){
            throw new EmailAlreadyExistsException("email already exists : "+dto.getEmail());
        }

        User user = User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return repository.save(user);
    }

    @Override
    public User getUserById(long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found, id : "+userId));
    }

    @Override
    public User getAllUser() {
        return null;
    }

    @Override
    public User updateUser(UserDto dto) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {

    }
}
