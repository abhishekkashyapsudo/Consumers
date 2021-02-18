package nagp.directservice.consumers.exceptions;

public class ConsumerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6170196487063827224L;
	private static final String ERROR_MESSAGE = "No Consumer exists with the passed consumer id";
	public ConsumerNotFoundException(String msg) {
		super(ERROR_MESSAGE +":" + msg);
	}
	
	public ConsumerNotFoundException() {
		super(ERROR_MESSAGE);
	}
}
