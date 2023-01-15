package com.bookrestAPI.exception;

@SuppressWarnings("serial")
public class AlreadyExistException extends RuntimeException {
	private String resourceName;
	private String resourceValue;

	public AlreadyExistException(String resourceName, String resourceValue) {
		super(String.format("%s already exists with ID: %s", resourceName, resourceValue));
		this.resourceName = resourceName;
		this.resourceValue = resourceValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

}
