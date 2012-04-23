package edu.kit.cm.kitcampusguide.standardtypes;

public class InitializationException extends Exception {
	public InitializationException() {
		super();
	}
	public InitializationException(String message) {
		super(message);
	}
	public InitializationException(Throwable cause) {
		super(cause);
	}
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
