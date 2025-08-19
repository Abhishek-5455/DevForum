package com.devforum.service;

import com.devforum.model.Tag;
import com.devforum.repository.TagRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagService {
	private final TagRepository tagRepository;

	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public Tag create(String name, String description) {
		Tag t = new Tag();
		t.setName(name);
		t.setDescription(description);
		return tagRepository.save(t);
	}

	public List<Tag> all() { return tagRepository.findAll(); }
} 