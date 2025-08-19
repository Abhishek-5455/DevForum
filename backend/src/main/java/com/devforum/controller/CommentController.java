package com.devforum.controller;

import com.devforum.model.Comment;
import com.devforum.model.User;
import com.devforum.service.CommentService;
import com.devforum.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	private final CommentService commentService;
	private final UserService userService;

	public CommentController(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Comment> create(Authentication auth,
									 @RequestParam Long postId,
									 @RequestParam String content,
									 @RequestParam(required = false) Long parentCommentId) {
		User u = userService.findByUsername(auth.getName()).orElseThrow();
		Comment c = commentService.createComment(u.getId(), postId, content, parentCommentId);
		return ResponseEntity.ok(c);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		commentService.deleteComment(id);
		return ResponseEntity.noContent().build();
	}
} 