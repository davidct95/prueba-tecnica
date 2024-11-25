package com.prueba.tareas.service;

import com.prueba.tareas.dto.UserDto;
import com.prueba.tareas.model.User;
import com.prueba.tareas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserDto userDto){

        String encryptedPassword = passwordEncoder.encode(userDto.password());
        User user = new User(userDto.username(), encryptedPassword);
        userRepository.save(user);

    }

}
