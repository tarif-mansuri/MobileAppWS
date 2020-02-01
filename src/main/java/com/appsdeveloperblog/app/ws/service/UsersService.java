package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface UsersService {
	public UserDTO createUser(UserDTO user);

	public UserDTO getUser(String userId);

	public UserDTO getUserByUserName(String userName);
}
