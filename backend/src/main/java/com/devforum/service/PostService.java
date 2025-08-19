package com.devforum.service;

import com.devforum.model.Post;
import com.devforum.model.Tag;
import com.devforum.model.User;
import com.devforum.repository.PostRepository;
import com.devforum.repository.TagRepository;
import com.devforum.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final TagRepository tagRepository;

	public PostService(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
	}

	@Transactional
	public Post createPost(Long authorId, String title, String content, Set<String> tagNames) {
		User author = userRepository.findById(authorId).orElseThrow();
		Post post = new Post();
		post.setAuthor(author);
		post.setTitle(title);
		post.setContent(content);
		Set<Tag> tags = new HashSet<>();
		for (String name : tagNames) {
			Tag tag = tagRepository.findByNameIgnoreCase(name).orElseGet(() -> {
				Tag t = new Tag();
				t.setName(name);
				return tagRepository.save(t);
			});
			tags.add(tag);
		}
		post.setTags(tags);
		return postRepository.save(post);
	}

	public List<Post> search(String q, List<String> tags, String sort) {
		if (tags != null && !tags.isEmpty()) {
			return postRepository.findDistinctByTags_NameIn(tags);
		}
		if ("popular".equalsIgnoreCase(sort)) {
			return postRepository.findAllByOrderByLikeCountDesc();
		}
		if ("new".equalsIgnoreCase(sort)) {
			return postRepository.findAllByOrderByCreatedAtDesc();
		}
		return postRepository.findByTitleContainingIgnoreCase(q == null ? "" : q);
	}

	public List<Post> searchByTitle(String q) {
		return postRepository.findByTitleContainingIgnoreCase(q);
	}

	public Post getPost(Long id) { return postRepository.findById(id).orElseThrow(); }

	@Transactional
	public Post updatePost(Long postId, String title, String content) {
		Post p = postRepository.findById(postId).orElseThrow();
		p.setTitle(title);
		p.setContent(content);
		return postRepository.save(p);
	}

	@Transactional
	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

	@Transactional
	public Post likePost(Long postId, boolean dislike) {
		Post p = postRepository.findById(postId).orElseThrow();
		if (dislike) {
			p.setDislikeCount(p.getDislikeCount() + 1);
		} else {
			p.setLikeCount(p.getLikeCount() + 1);
		}
		return postRepository.save(p);
	}
} 