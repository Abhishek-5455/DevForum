package com.devforum.service;

import com.devforum.model.Comment;
import com.devforum.model.Post;
import com.devforum.model.User;
import com.devforum.repository.CommentRepository;
import com.devforum.repository.PostRepository;
import com.devforum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public Comment createComment(Long authorId, Long postId, String content, Long parentCommentId) {
		User author = userRepository.findById(authorId).orElseThrow();
		Post post = postRepository.findById(postId).orElseThrow();
		Comment c = new Comment();
		c.setAuthor(author);
		c.setPost(post);
		c.setContent(content);
		if (parentCommentId != null) {
			Comment parent = commentRepository.findById(parentCommentId).orElseThrow();
			c.setParentComment(parent);
		}
		return commentRepository.save(c);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
} 