package com.devforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(name = "uk_users_username", columnNames = "username"),
		@UniqueConstraint(name = "uk_users_email", columnNames = "email")
})
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String username;

	@Column(nullable = false, length = 120)
	private String email;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column(length = 250)
	private String bio;

	@Column(length = 255)
	private String avatarUrl;

	@Column(length = 255)
	private String githubUrl;

	@Column(length = 255)
	private String linkedinUrl;

	@Column(nullable = false)
	private Integer reputation = 0;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();

	public Long getId() { return id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getBio() { return bio; }
	public void setBio(String bio) { this.bio = bio; }
	public String getAvatarUrl() { return avatarUrl; }
	public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
	public String getGithubUrl() { return githubUrl; }
	public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
	public String getLinkedinUrl() { return linkedinUrl; }
	public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
	public Integer getReputation() { return reputation; }
	public void setReputation(Integer reputation) { this.reputation = reputation; }
	public Set<Role> getRoles() { return roles; }
	public void setRoles(Set<Role> roles) { this.roles = roles; }
} 