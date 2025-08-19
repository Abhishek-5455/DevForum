package com.devforum.controller;

import com.devforum.model.Tag;
import com.devforum.service.TagService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
	private final TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping
	public ResponseEntity<List<Tag>> all() {
		return ResponseEntity.ok(tagService.all());
	}

	@PostMapping
	public ResponseEntity<Tag> create(@RequestParam String name, @RequestParam(required = false) String description) {
		return ResponseEntity.ok(tagService.create(name, description));
	}
} 