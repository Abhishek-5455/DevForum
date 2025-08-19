package com.devforum.controller;

import com.devforum.model.Post;
import com.devforum.model.User;
import com.devforum.service.PostService;
import com.devforum.service.UserService;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	private final PostService postService;
	private final UserService userService;

	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<Post>> search(@RequestParam(name = "q", required = false) String q,
											 @RequestParam(name = "tags", required = false) List<String> tags,
											 @RequestParam(name = "sort", required = false) String sort) {
		return ResponseEntity.ok(postService.search(q, tags, sort));
	}

	@PostMapping
	public ResponseEntity<Post> create(Authentication auth,
								   @RequestParam String title,
								   @RequestParam String content,
								   @RequestParam(required = false) Set<String> tags) {
		User u = userService.findByUsername(auth.getName()).orElseThrow();
		Post p = postService.createPost(u.getId(), title, content, tags == null ? Set.of() : tags);
		return ResponseEntity.ok(p);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> get(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPost(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestParam String title, @RequestParam String content) {
		return ResponseEntity.ok(postService.updatePost(id, title, content));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/like")
	public ResponseEntity<Post> like(@PathVariable Long id) {
		return ResponseEntity.ok(postService.likePost(id, false));
	}

	@PostMapping("/{id}/dislike")
	public ResponseEntity<Post> dislike(@PathVariable Long id) {
		return ResponseEntity.ok(postService.likePost(id, true));
	}
} 