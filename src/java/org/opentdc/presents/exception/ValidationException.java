package org.opentdc.presents.exception;

import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.opentdc.presents.PresentData;

public class ValidationException extends BaseException {

	private static final long serialVersionUID = 4518221114L;

	// see java.io.Serializable:
	// version number for serializable class
	// reserved in uids.txt: 45182.2.1.1.1.4 # ValidationException

	public ValidationException(Map<String, PresentData> data) {
		super(Status.BAD_REQUEST, "Validation failed", data);
	}
}
