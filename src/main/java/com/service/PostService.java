package com.service;

import java.util.List;

import com.payloads.PostDto;
import com.response.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto,int userId,int categoryId);
	public PostDto updatePost(PostDto postDto,int postId);
	public void deletePost(int postId);
	public PostDto getPostById(int postId);
	
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	public List<PostDto>getPostByUser(int userId);
	
	public List<PostDto>getPostByCategory(int categoryId);
	
	public List<PostDto>searchPosts(String keyword);
	
	

}
