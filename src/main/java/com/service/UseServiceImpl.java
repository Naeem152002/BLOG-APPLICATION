package com.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.config.AppConstants;
import com.entities.Post;
import com.entities.Role;
import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.payloads.RoleDto;
import com.payloads.UserDto;
import com.repo.PostRepo;
import com.repo.RoleRepo;
import com.repo.UserRepo;
@Service
public class UseServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PostRepo postRepo;
	

	@Override
	public UserDto insertAdmin(UserDto userDto) {
		User use=this.userRepo.findByEmail(userDto.getEmail());
		if(use!=null) {
			return null;
		}else {
		User user=modelMapper.map(userDto, User.class);
		Role role=roleRepo.findById(AppConstants.ADMIN_USER).get();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.getRoles().add(role);
		User save=userRepo.save(user);
		UserDto dto=modelMapper.map(save, UserDto.class);
		return dto;
	}}

	@Override
	public UserDto updateUser(UserDto userDto, int id) {
		User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(passwordEncoder.encode(userDto.getAbout()));
		User save=userRepo.save(user);
		UserDto dto=modelMapper.map(save, UserDto.class);
		return dto;
	}

	@Override
	public void deleteUser(int id) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
		user.getRoles().clear();
        this.userRepo.save(user);
		userRepo.delete(user);
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>allUsers=this.userRepo.findAll();
    List<UserDto>dtos=allUsers.stream().map(user->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    return dtos;
	}

	@Override
	public UserDto getSingle(int id) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto insertUser(UserDto userDto) {
		User use=userRepo.findByEmail(userDto.getEmail());
		if(use!=null) {
			return null;
		}else {
		User user=modelMapper.map(userDto, User.class);
		Role role=roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.getRoles().add(role);
		User save=userRepo.save(user);
		UserDto dto=modelMapper.map(save, UserDto.class);
		return dto;
	}}
	@Override
	public UserDto getProfile(String email){
        UserDto dto = new UserDto();
            User user = userRepo.findByEmail(email);
            if (user!=null) {
            	dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setPassword(user.getPassword());
                dto.setAbout(user.getAbout());
                Set<RoleDto> roleDtos = user.getRoles().stream().map(role -> {
                    RoleDto roleDto = new RoleDto();
                    roleDto.setId(role.getId());
                    roleDto.setName(role.getName());  // Assuming Role entity has a field 'roleName'
                    return roleDto;
                }).collect(Collectors.toSet());
                dto.setRoles(roleDtos);
                return dto;
            } else {
                throw new ResourceNotFoundException("User","Id",0);

    }}
	
	@Override
	public UserDto updateProfile(UserDto userDto,String email){
            User user = userRepo.findByEmail(email);
            int id=user.getId();
            User save=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
            save.setName(userDto.getName());
    	    save.setEmail(userDto.getEmail());
    	    save.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	    save.setAbout(userDto.getAbout());
    	    User save1=userRepo.save(save);
    		return modelMapper.map(save1, UserDto.class);
    }

	@Override
	public void deleteProfile(String email) {
		 User user = userRepo.findByEmail(email);
         int id=user.getId();
         user.getRoles().clear();

         // Delete all posts associated with the user
         for (Post post : user.getPosts()) {
             post.setUser(null); // Unlink the post from the user
             postRepo.delete(post); // Delete the post
         }
         User save=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
         userRepo.delete(save);
     }
	}



