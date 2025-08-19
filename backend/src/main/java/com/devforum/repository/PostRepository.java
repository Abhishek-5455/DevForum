package com.devforum.repository;

import com.devforum.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByTitleContainingIgnoreCase(String title);
	List<Post> findDistinctByTags_NameIn(List<String> tagNames);
	List<Post> findAllByOrderByCreatedAtDesc();
	List<Post> findAllByOrderByLikeCountDesc();
} 