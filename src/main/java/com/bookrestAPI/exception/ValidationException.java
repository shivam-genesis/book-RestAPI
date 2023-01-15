package com.bookrestAPI.exception;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {
	private String resourceName;
	private String resourceValue;

	public ValidationException(String resourceName, String resourceValue) {
		super(String.format("%s length must be greater: %s", resourceName, resourceValue));
		this.resourceName = resourceName;
		this.resourceValue = resourceValue;
	}
	
	public ValidationException(String resourceName) {
		super(String.format("%s cannot be null!!"));
		this.resourceName = resourceName;
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
