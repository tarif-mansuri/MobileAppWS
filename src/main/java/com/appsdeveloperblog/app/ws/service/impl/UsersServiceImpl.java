package com.appsdeveloperblog.app.ws.service.impl;

import java.util.List;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreatRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotDeleteRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotUpdateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.NoRecordFoundException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

public class UsersServiceImpl implements UsersService {

	DAO database;

	public UsersServiceImpl() {
		this.database = new MySQLDAO();
	}

	UserProfileUtils userProfileUtils = new UserProfileUtils();

	@Override
	public UserDTO createUser(UserDTO userDto) {
		UserDTO returnValue = null;
		userProfileUtils.validateRequiredFields(userDto);// Validate the required fields
		UserDTO existingUser = this.getUserByUserName(userDto.getEmail());// Check if user already exists
		if (existingUser != null) {
			throw new CouldNotCreatRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
		}
		String userId = userProfileUtils.generateUserId(30);// Generate secure public user id
		userDto.setUserId(userId);
		String salt = userProfileUtils.getSalt(30);// generate salt
		// generate secure password
		String encriptedPassword = userProfileUtils.generateSecurePassword(userDto.getPassword(), salt);
		userDto.setSalt(salt);
		userDto.setEncryptedPassword(encriptedPassword);
		returnValue = this.saveUser(userDto);// Record data into a database
		return returnValue;// return back the user profile
	}

	private UserDTO saveUser(UserDTO userDto) {
		UserDTO returnValue = null;
		try {
			this.database.openConnection();
			returnValue = database.saveUser(userDto);
		} finally {
			database.closeConnection();
		}
		return returnValue;
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;
		if (userName == null || userName.isEmpty()) {
			return userDto;
		}
		try {
			this.database.openConnection();
			userDto = database.getUserByName(userName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		finally {
			database.closeConnection();
		}
		return userDto;
	}

	@Override
	public UserDTO getUser(String userId) {
		UserDTO userDto = null;
		if (userId == null || userId.isEmpty()) {
			return userDto;
		}
		try {
			this.database.openConnection();
			userDto = database.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		finally {
			database.closeConnection();
		}
		return userDto;
	}

	@Override
	public List<UserDTO> getUsers(int start, int limit) {
		List<UserDTO> users = null;
		
		try {
			this.database.openConnection();
			users = database.getUsers(start, limit);
		}finally{
			database.closeConnection();
		}
		return users;
	}

	@Override
	public void updateUserDetails(UserDTO userDto) {
		try {
			//Connect to database
			this.database.openConnection();
			this.database.updateUserProfile(userDto);
		}catch(Exception e) {
			throw new CouldNotUpdateRecordException(e.getMessage());
		}finally {
			this.database.closeConnection();
		}
		
	}

	@Override
	public void deleteUser(UserDTO userDto) {
		try {
			this.database.openConnection();
			this.database.deleteUserProfile(userDto);
		}catch(Exception e) {
			throw new CouldNotDeleteRecordException(e.getMessage());
		}finally {
			this.database.closeConnection(); 
		}
		
	}
}
