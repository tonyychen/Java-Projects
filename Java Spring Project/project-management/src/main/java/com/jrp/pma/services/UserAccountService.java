package com.jrp.pma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entities.UserAccount;

@Service
public class UserAccountService {
	@Autowired
	UserAccountRepository userRepo;
	
	public UserAccount save(UserAccount user) {
		return userRepo.save(user);
	}
	
}
