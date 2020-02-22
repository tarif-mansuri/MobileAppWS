package com.appsdeveloperblog.app.ws.io.dao;

import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface DAO {
	void openConnection();

	UserDTO getUserByName(String userName);

	void closeConnection();

	UserDTO saveUser(UserDTO userDto);

	UserDTO getUserById(String id);

	void updateUserProfile(UserDTO userDto);

	List<UserDTO> getUsers(int start, int limit);
}
