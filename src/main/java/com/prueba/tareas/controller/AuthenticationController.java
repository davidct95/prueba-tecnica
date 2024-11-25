package com.prueba.tareas.controller;

import com.prueba.tareas.configuration.security.DatosJWTToken;
import com.prueba.tareas.configuration.security.TokenService;
import com.prueba.tareas.dto.UserDto;
import com.prueba.tareas.model.User;
import com.prueba.tareas.repository.UserRepository;
import com.prueba.tareas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticationUser(@RequestBody UserDto userDto) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password());
        var authenticatedUser = authenticationManager.authenticate(authenticationToken);
        var jwtToken = tokenService.generarToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

}
