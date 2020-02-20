package com.appsdeveloperblog.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appsdeveloperblog.app.ws.ui.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.response.ErrorMessages;

@Provider
public class CouldNotCreatRecordExceptionMapper implements ExceptionMapper<CouldNotCreatRecordException> {
	public Response toResponse(CouldNotCreatRecordException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.RECORD_ALREADY_EXISTS.name(), "http://appsdeveloper.com");
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
