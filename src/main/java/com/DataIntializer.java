package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.config.AppConstants;
import com.entities.Role;
import com.repo.RoleRepo;
@Component
public class DataIntializer implements CommandLineRunner {
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		
		try {
		Role role1=new Role();
		role1.setId(AppConstants.ADMIN_USER);
		role1.setName("ROLE_ADMIN");
		
		
		Role role2=new Role();
		role2.setId(AppConstants.NORMAL_USER);
		role2.setName("ROLE_USER");
		
		
		List<Role>role=List.of(role1,role2);
		
		List<Role>roles=roleRepo.saveAll(role);
		
		roles.forEach(r->{
		System.out.println(r.getName());
		});
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		}

}
