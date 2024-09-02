package com.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.payloads.RoleDto;
import com.payloads.UserDto;
import com.response.ApiResponse;
import com.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	
     @PutMapping("/admin/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int id) {
		return new  ResponseEntity<UserDto>(userService.updateUser(userDto, id),HttpStatus.OK);
	}
     
     @DeleteMapping("/admin/{id}")
     public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id) {
         UserDto dto = userService.getSingle(id);
        String name= dto.getEmail();
      // Collect all roles of the user into a string
         String roles = dto.getRoles().stream()
                          .map(RoleDto::getName) // Assuming Role has a getName() method
                          .collect(Collectors.joining(", "));
         // Delete the user
         userService.deleteUser(id);

         // Determine the message based on the role of the deleted user
         String message = roles+"=" +name+ " deleted successfully";

         // Return the response with the appropriate message
         return new ResponseEntity<>(new ApiResponse(message, true), HttpStatus.OK);
     }


     @GetMapping("/admin/")
     public ResponseEntity<List<UserDto>> getAllUsers() {
    	 return ResponseEntity.ok(userService.getAllUsers());
     }
     
     @GetMapping("/admin/{id}")
     public ResponseEntity<UserDto> getSingleUser(@PathVariable int id) {
    	 return ResponseEntity.ok(userService.getSingle(id));
     }
     @GetMapping("/admin-user/profile")
     public ResponseEntity<UserDto> getMyProfile(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         System.out.println(email);
         return  ResponseEntity.ok(userService.getProfile(email));
     }
 	@PutMapping("/admin-user/profile/update")
 	public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto userDto){
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 		String email = authentication.getName();
 		return  ResponseEntity.ok(userService.updateProfile(userDto,email));
 	}
 	@DeleteMapping("/admin-user/profile/delete")
 	public ResponseEntity<ApiResponse> deleteProfile(){
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 		String email = authentication.getName();
        UserDto dto = userService.getProfile(email);
     // Collect all roles of the user into a string
        String roles = dto.getRoles().stream()
                         .map(RoleDto::getName) // Assuming Role has a getName() method
                         .collect(Collectors.joining(", "));
        // Delete the user
        userService.deleteProfile(email);

        // Determine the message based on the role of the deleted user
        String message = roles+"=" +email+ " deleted successfully";

        // Return the response with the appropriate message
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatus.OK);
 	}
}
