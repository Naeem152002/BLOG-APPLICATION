package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Category;
import com.entities.Post;
import com.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post>findByCategory(Category category);
	List<Post>findByUser(User user);
	List<Post> findByTitleContaining(String title);
}
