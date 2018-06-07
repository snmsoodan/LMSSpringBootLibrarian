package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseDAO<T> {
		  
	@Autowired
//	@Qualifier(value="mySqlTemplate")
	JdbcTemplate mySqlTemplate;
	

}
