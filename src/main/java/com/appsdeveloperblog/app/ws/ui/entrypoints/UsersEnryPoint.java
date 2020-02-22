package com.appsdeveloperblog.app.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.annotations.Secured;
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

	@Secured
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileResponse testingJersy(@PathParam("id") String userId) {
		UserProfileResponse returnValue = null;

		UsersService userService = new UsersServiceImpl();
		UserDTO userDto = userService.getUser(userId);

		returnValue = new UserProfileResponse();
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserProfileResponse> getUsers(@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("50") @QueryParam("limit") int limit) {
		UsersService userService = new UsersServiceImpl();
		List<UserDTO> users = userService.getUsers(start, limit);
		
		//prepare return value
		List<UserProfileResponse> returnValue = new ArrayList<UserProfileResponse>();
		for(UserDTO userDto : users) {
			UserProfileResponse userModel = new UserProfileResponse();
			BeanUtils.copyProperties(userDto, userModel);
			userModel.setHref("/users/"+userDto.getUserId());
			returnValue.add(userModel);
		}
		return returnValue;
	}
}
