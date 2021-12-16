package com.bookstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookstore.entity.Users;
import com.bookstore.utility.HashGenerationException;

public class UserDAO extends JpaDAO<Users> implements GenericDAO<Users> {

	@Override
	public Users get(Object userId) {
		return super.find(Users.class, userId);
	}

	public Users findByEmail(String email) {
		List<Users> listUsers = super.findWithNamedQuery("Users.findByEmail", "email", email);
		
		if (listUsers != null && listUsers.size() > 0) {
			return listUsers.get(0);
		}
		
		return null;
	}
	
	public boolean checkLogin(String email, String password) throws HashGenerationException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("password", password);
		
		List<Users> listUsers = super.findWithNamedQuery("Users.checkLogin", parameters);
		
		if (listUsers.size() == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public void delete(Object userId) {
		super.delete(Users.class, userId);
	}

	@Override
	public List<Users> listAll() {
		return super.findWithNamedQuery("Users.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Users.countAll");
	}

}
