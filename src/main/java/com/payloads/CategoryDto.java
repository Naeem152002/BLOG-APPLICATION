package com.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	@NotEmpty
	@Size(min=4, message="Min size of category title is 4")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=10, message="Min size of category desc is 10")
	private String categoryDescripation;	

	
	
	
}
