//$Id$
package com.appsdeveloperblog.app.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.appsdeveloperblog.app.ws.ui.model.request.RequestOperation;

@XmlRootElement
public class DeleteUserProfileResponse {
	private RequestOperation requestOperation;
	private ResponseStatus responseStatus;

	public RequestOperation getRequestOperation() {
		return requestOperation;
	}

	public void setRequestOperation(RequestOperation requestOperation) {
		this.requestOperation = requestOperation;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
}
