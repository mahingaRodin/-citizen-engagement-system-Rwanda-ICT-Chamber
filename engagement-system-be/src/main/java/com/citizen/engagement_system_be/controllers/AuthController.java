package com.citizen.engagement_system_be.controllers;

import com.citizen.engagement_system_be.dtos.AuthenticationRequest;
import com.citizen.engagement_system_be.dtos.AuthenticationResponse;
import com.citizen.engagement_system_be.dtos.SignupRequestDTO;
import com.citizen.engagement_system_be.dtos.UserDTO;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.AuthService;
import com.citizen.engagement_system_be.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v3/auth")
@Tag(name = "User Management", description = "APIs for managing users")
public class AuthController {
    private final AuthService userService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService userService, UserRepository userRepo, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signupRequest) {
        try {
            UserDTO createdUser = userService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>("User Already Exists!", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Created. Please try again later", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Update user")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest user) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch(BadCredentialsException e) {
           throw new BadCredentialsException("Invalid email or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(user.getEmail());
        Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();


        if(optionalUser.isPresent()) {
            authenticationResponse.setToken(token);
            authenticationResponse.setUserRole(optionalUser.get().getRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return ResponseEntity.ok(authenticationResponse).getBody();
    }

}
