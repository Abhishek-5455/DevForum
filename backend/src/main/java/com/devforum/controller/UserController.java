package com.devforum.controller;

import com.devforum.model.User;
import com.devforum.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication auth) {
		return ResponseEntity.ok(userService.findByUsername(auth.getName()).orElseThrow());
	}

	@PutMapping("/me")
	public ResponseEntity<?> updateMe(Authentication auth,
								   @RequestParam(required = false) String bio,
								   @RequestParam(required = false) String avatarUrl,
								   @RequestParam(required = false) String githubUrl,
								   @RequestParam(required = false) String linkedinUrl) {
		User u = userService.findByUsername(auth.getName()).orElseThrow();
		User updated = userService.updateProfile(u.getId(), bio, avatarUrl, githubUrl, linkedinUrl);
		return ResponseEntity.ok(updated);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> listAll() {
		return ResponseEntity.ok(userService.findAllUsers());
	}
} 