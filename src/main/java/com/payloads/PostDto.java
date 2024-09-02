package com.payloads;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
     
	
	private int postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	

	
	private Set<CommentDto>comments=new HashSet<>();
	
	private CategoryDto category;
	
	private UserDto user;
	
	
	
}
