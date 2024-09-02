package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payloads.CommentDto;
import com.response.ApiResponse;
import com.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto,@PathVariable int postId){
		
CommentDto createComment=this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	
	}
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse>createComment(@PathVariable int commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
	}

}
