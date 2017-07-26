package com.dove.sprout.common.exception;

public class BaseException extends RuntimeException{

	public static final int ERROR_CODE = 500000;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	private Throwable cause;
	public BaseException(int code, String message, Throwable cause) {
		super(message,cause);
		this.code = code;
		this.message = message;
		this.cause = cause;
	}
	public BaseException(String message, Throwable cause) {
		super(message,cause);
		this.message = message;
		this.cause = cause;
	}
	public BaseException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	public BaseException() {
		super();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Throwable getCause() {
		return cause;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	 
	
	
}
