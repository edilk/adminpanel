package com.example.adminpanel.service;

import com.example.adminpanel.dto.JwtRequest;
import com.example.adminpanel.dto.JwtResponse;
import com.example.adminpanel.dto.RegistrationUserDTO;
import com.example.adminpanel.dto.UserDTO;
import com.example.adminpanel.entity.User;
import com.example.adminpanel.exceptions.AppError;
import com.example.adminpanel.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new AppError(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Wrong username or password"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(RegistrationUserDTO registrationUserDto) {

        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "This username already exists"), HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userService.createNewUser(registrationUserDto);

            return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername()));
        } catch (Exception e) {
            String errorMessage = "User " + registrationUserDto.getUsername() + " already exists";
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

}
