package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	
	

}
