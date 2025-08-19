package com.devforum.repository;

import com.devforum.model.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByNameIgnoreCase(String name);
} 