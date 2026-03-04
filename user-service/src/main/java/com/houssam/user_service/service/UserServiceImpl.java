package com.houssam.user_service.service;

import com.houssam.user_service.EmailAlreadyExistsException;
import com.houssam.user_service.dto.UserDto;
import com.houssam.user_service.entity.User;
import com.houssam.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @Override
    public User updateUser(long id,UserDto dto) {
        boolean exists = repository.existsById(id);

        if(!exists){
            ;
        }

        User user = repository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found, id :"+id)
        );

        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode((dto.getPassword())));

        return repository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteById(userId);
    }
}
