package com.appsdeveloperblog.app.ws.ui.model.response;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	NO_RECORD_FOUND("Record with provided id not found"),
	AUTHENTICATION_FAILED("Authentication Failed"),
	COULD_NOT_UPDATE_RECORD("Could not update record."),
	COULD_NOT_DELETE_RECORD("Could not Delete record.");
	

	private String errorMessage;

	ErrorMessages(String message) {
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
