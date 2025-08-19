package com.devforum.service;

import com.devforum.model.Role;
import com.devforum.model.User;
import com.devforum.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User signup(String username, String email, String rawPassword) {
		if (userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already taken");
		}
		if (userRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Email already registered");
		}
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(rawPassword));
		user.setRoles(Collections.singleton(Role.MEMBER));
		return userRepository.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public User updateProfile(Long userId, String bio, String avatarUrl, String githubUrl, String linkedinUrl) {
		User user = userRepository.findById(userId).orElseThrow();
		user.setBio(bio);
		user.setAvatarUrl(avatarUrl);
		user.setGithubUrl(githubUrl);
		user.setLinkedinUrl(linkedinUrl);
		return userRepository.save(user);
	}
} 