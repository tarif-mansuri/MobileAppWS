package com.appsdeveloperblog.app.ws.service;

import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface UsersService {
	public UserDTO createUser(UserDTO user);

	public UserDTO getUser(String userId);

	public UserDTO getUserByUserName(String userName);

	public List<UserDTO> getUsers(int start, int limit);

	public void updateUserDetails(UserDTO userDto);

	public void deleteUser(UserDTO userDto);
}
