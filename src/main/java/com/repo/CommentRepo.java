package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
