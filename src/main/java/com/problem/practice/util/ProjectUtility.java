package com.problem.practice.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.problem.practice.entity.UniqueId;
import com.problem.practice.repository.UniqueIdRepository;

@Component
public class ProjectUtility {

	@Autowired
	private UniqueIdRepository idrepo;
	
	public String getCurrentUserId() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof UserDetails) {
	        return ((UserDetails) principal).getUsername(); 
	    } else {
	        return principal.toString();
	    }
	}
	
	
	public Integer generateId() {
		Optional<UniqueId> uniqueId = idrepo.findById("123");
		if(uniqueId.isEmpty()) {
			UniqueId id = new UniqueId();
			id.setId("123");
			id.setCounterValue(1);
			idrepo.save(id);
			return 1;
		}
		
		Integer id = uniqueId.get().getCounterValue()+1;
		
		uniqueId.get().setCounterValue(id);
		idrepo.save(uniqueId.get());
		
		return id;
		
	}
	
	
}
