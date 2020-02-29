package com.appsdeveloperblog.app.ws.service.impl;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

public class AuthenticationServiceImpl implements AuthenticationService {

	DAO database;

	@Override
	public UserDTO authenticate(String userName, String password) throws AuthenticationException {
		UsersService userService = new UsersServiceImpl();
		UserDTO storedUser = userService.getUserByUserName(userName);
		if (storedUser == null) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}
		String encryptedPassword = null;
		encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());
		boolean authenticated = false;
		if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
			if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
				authenticated = true;
			}
		}
		if (!authenticated) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}
		return storedUser;
	}

	@Override
	public String issueAccessToken(UserDTO userDto) throws AuthenticationException {
		String returnValue = null;
		String accessToken = userDto.getUserId() + userDto.getSalt();
		byte[] encryptedAccessToken = null;
		try {
			encryptedAccessToken = new UserProfileUtils().encrypt(userDto.getEncryptedPassword(), accessToken);

		} catch (InvalidKeySpecException e) {
			Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, e);
			throw new AuthenticationException("Failed to issue secure access token");
		}
		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
		int tokenLength = encryptedAccessTokenBase64Encoded.length();
		String tokenToSaveToDataBase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
		returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
		userDto.setToken(tokenToSaveToDataBase);
		updateUserProfile(userDto);
		return returnValue;
	}

	private void updateUserProfile(UserDTO userDto) {
		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUserProfile(userDto);
		}finally {
			database.closeConnection();
		}
	}

	@Override
	public void resetSecurityCredentials(String userpassword, UserDTO userDto) {
		//Generate a new salt
		UserProfileUtils userProfileUtils = new UserProfileUtils();
		String salt = userProfileUtils.getSalt(30);
		//Generate a new password
		String securePassword = userProfileUtils.generateSecurePassword(userpassword, salt);
		userDto.setSalt(salt);
		userDto.setEncryptedPassword(securePassword);
		updateUserProfile(userDto);
	}
}
