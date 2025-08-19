package com.devforum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Integer likeCount = 0;

	@Column(nullable = false)
	private Integer dislikeCount = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToMany
	@JoinTable(name = "post_tags",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	public Long getId() { return id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	public Integer getLikeCount() { return likeCount; }
	public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
	public Integer getDislikeCount() { return dislikeCount; }
	public void setDislikeCount(Integer dislikeCount) { this.dislikeCount = dislikeCount; }
	public User getAuthor() { return author; }
	public void setAuthor(User author) { this.author = author; }
	public Set<Tag> getTags() { return tags; }
	public void setTags(Set<Tag> tags) { this.tags = tags; }
} 