package com.devforum.controller;

import com.devforum.model.User;
import com.devforum.security.JwtTokenProvider;
import com.devforum.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestParam @NotBlank String username,
									 @RequestParam @Email String email,
									 @RequestParam @NotBlank String password) {
		User user = userService.signup(username, email, password);
		String token = jwtTokenProvider.generateToken(user.getUsername());
		return ResponseEntity.ok(new TokenResponse(token));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username,
								  @RequestParam String password) {
		User user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("Invalid credentials");
		}
		String token = jwtTokenProvider.generateToken(user.getUsername());
		return ResponseEntity.ok(new TokenResponse(token));
	}

	public record TokenResponse(String token) {}
} 