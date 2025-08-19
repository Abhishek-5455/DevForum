package com.devforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	@JsonIgnore
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	@JsonIgnore
	private Comment parentComment;

	public Long getId() { return id; }
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	public User getAuthor() { return author; }
	public void setAuthor(User author) { this.author = author; }
	public Post getPost() { return post; }
	public void setPost(Post post) { this.post = post; }
	public Comment getParentComment() { return parentComment; }
	public void setParentComment(Comment parentComment) { this.parentComment = parentComment; }
} 