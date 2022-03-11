package com.jrp.pma.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jrp.pma.entities.UserAccount;

@Repository
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {
	
}
