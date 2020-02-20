package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface DAO {
	void openConnection();

	UserDTO getUserByName(String userName);

	void closeConnection();

	UserDTO saveUser(UserDTO userDto);

	UserDTO getUserById(String id);

	void updateUserProfile(UserDTO userDto);
}
