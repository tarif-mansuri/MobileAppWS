package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreatRecordException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

public class UsersServiceImpl implements UsersService{

	DAO database;
	public UsersServiceImpl() {
		this.database = new MySQLDAO();
	}
	
	UserProfileUtils userProfileUtils = new UserProfileUtils();
	public UserDTO createUser(UserDTO userDto) {
		//Validate the required fields
		userProfileUtils.validateRequiredFields(userDto);
		
		//Check if user already exists
		UserDTO existingUser = this.getUserByUserName(userDto.getEmail());
		if(existingUser!=null) {
			throw new CouldNotCreatRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
		}
		//Create an entity object
		
		//Generate secure public user id
		String userId = userProfileUtils.generateUserId(30);
		userDto.setUserId(userId);
		
		//generate salt
		String salt = userProfileUtils.getSalt(30);
		
		//generate secure password
		String encriptedPassword = userProfileUtils.generateSecurePassword(userDto.getPassword(), salt);
		userDto.setSalt(salt);
		userDto.setEncryptedPassword(encriptedPassword);
		//Record data into a database
		
		//return back the user profile
		
		
		
		return null;
	}
	private UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;
		if(userName == null ||userName.isEmpty()) {
			return userDto;
		}
		try {
			this.database.openConnection();
			userDto = database.getUserByName(userName);
		}finally {
			database.closeConnection();
		}
		return userDto;
	}

}
