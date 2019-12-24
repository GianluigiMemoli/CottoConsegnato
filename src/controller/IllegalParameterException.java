package controller;


public class IllegalParameterException extends RuntimeException {
	IllegalParameterException(String msg, String parameterName){
		super(msg);
		this.parameterName = parameterName;
	}
	
	public String getFaultyParameter() {
		return parameterName;
	}
	
	private String parameterName;
}
