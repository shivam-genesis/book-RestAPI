package com.bookrestAPI.exception;

public class ApiResponse {

	public ApiResponse(String message, Boolean success) {
		super();
		this.message = message;
		this.success = success;
	}

	private String message;
	private Boolean success;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
