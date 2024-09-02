package com.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.entities.Category;
import com.entities.Post;
import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.payloads.PostDto;
import com.repo.CategoryRepo;
import com.repo.PostRepo;
import com.repo.UserRepo;
import com.response.PostResponse;
@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private CategoryRepo  categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		Post post=modelMapper.map(postDto, Post.class);
		post.setCategory(category);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setImageName("Defautl.image");
		Post save=postRepo.save(post);
		return modelMapper.map(save, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		 Post save=postRepo.save(post);
		    return modelMapper.map(save, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
     Sort sort=null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost=this.postRepo.findAll(p);
		List<Post>allPosts=pagePost.getContent();
		List<PostDto>postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", userId));
		List<Post>posts=this.postRepo.findByUser(user);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post>posts=this.postRepo.findByCategory(cat);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post>posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
