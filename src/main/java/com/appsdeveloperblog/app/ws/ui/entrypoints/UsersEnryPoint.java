package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.request.CreateUserRequest;
import com.appsdeveloperblog.app.ws.ui.response.UserProfileResponse;

@Path("/users")
public class UsersEnryPoint {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileResponse createUser(CreateUserRequest requestObject) {
		UserProfileResponse response = new UserProfileResponse();

		// Prepare UserDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);

		// Create new user
		UsersService userService = new UsersServiceImpl();
		UserDTO createdUserProfile = userService.createUser(userDto);

		// Prepare Response
		BeanUtils.copyProperties(createdUserProfile, response);

		return response;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String testingJersy() {
		return "Workin hard";
	}
}
