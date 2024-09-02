package com.cos.photogramstart.handler.ex;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CustomException(String msg) {
		super(msg);
	}
}
