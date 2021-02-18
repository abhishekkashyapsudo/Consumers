package nagp.directservice.consumers.models;

public class Consumer {
	private static int currId = 10000000;

	/**
	 * Unique id of the consumer.
	 * This id is uniquely generated by the application
	 */
	private String consumerId;

	/**
	 * Full name of the consumer
	 */
	private String name;

	/**
	 * String state
	 */
	private String state;
	
	/**
	 * String address
	 */
	private String address;
	
	private String phone;
	
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public Consumer(String name, String state, String address, String phone) {
		super();
		this.consumerId = "C" + currId ++;
		this.name = name;
		this.state = state;
		this.address = address;
		this.phone = phone;
	}
	
	
	

}
